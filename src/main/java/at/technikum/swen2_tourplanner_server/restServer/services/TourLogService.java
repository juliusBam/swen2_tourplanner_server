package at.technikum.swen2_tourplanner_server.restServer.services;

import at.technikum.swen2_tourplanner_server.Logging;
import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.entities.TourLog;
import at.technikum.swen2_tourplanner_server.dto.TourLogReqModel;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.RecordCreationErrorExc;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.RecordNotFoundExc;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourLogRepository;
import at.technikum.swen2_tourplanner_server.restServer.services.interfaces.ITourLogService;
import at.technikum.swen2_tourplanner_server.restServer.services.interfaces.ITourService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourLogService extends Logging implements ITourLogService {

    private final TourLogRepository tourLogRepository;
    private final ITourService tourService;

    public TourLogService(TourLogRepository tourLogRepository, TourService tourService) {
        this.tourLogRepository = tourLogRepository;
        this.tourService = tourService;
    }

    @Override
    public List<TourLog> getAllByTourId(Long id) {

        return this.tourLogRepository.findByTour_id(id);
    }

    @Override
    @Transactional
    public Tour createTourLog(TourLogReqModel newTourLogReq) {

        Long linkedTourId = newTourLogReq.getTourId();

        Tour linkedTour = this.tourService.getById(linkedTourId).orElseThrow(
                () -> {
                    Logging.logger.error("Error creating tour log for tour with id [{}], the tour log could not be found in the database", linkedTourId);
                    return new RecordNotFoundExc("Could not find the associated tour with id: " + linkedTourId);
                }
        );

        //todo check version

        TourLog newTourLog = new TourLog(newTourLogReq.getTimeStamp(), newTourLogReq.getComment(),
                                            newTourLogReq.getDifficulty(), newTourLogReq.getTotalTimeMinutes(),
                                            newTourLogReq.getRating(), linkedTour);

        linkedTour.addLog(newTourLog);

        Long newTourLogId = this.tourLogRepository.saveAndFlush(newTourLog).getId();

        //update tour calculated values, we have to refetch the tour to get the new logs
        this.tourService.updateCalculatedValues(linkedTour.getId(), this.getAllByTourId(linkedTour.getId()));

        return this.tourService.getById(linkedTourId).orElseThrow(
                () -> {
                    Logging.logger.error("Error creating tour log for tour with id [{}], the tour log could not be found in the database", linkedTourId);
                    return new RecordNotFoundExc("Could not find the associated tour with id: " + linkedTourId);
                }
        );

    }

    @Override
    @Transactional
    public Tour updateTourLog(TourLogReqModel updatedTourLog) {

        //if tour log is not present anymore do not allow an update
        this.tourLogRepository.findById(updatedTourLog.getId()).orElseThrow(
                () -> {
                    Logging.logger.error("Error updating the tour log with id [{}], the tour log could not be found in the database", updatedTourLog.getId());
                    return new RecordNotFoundExc("Tour log not found");
                }
        );

        Tour parentTour = this.tourService.getById(updatedTourLog.getTourId()).orElseThrow();

        TourLog newTourLog = new TourLog(updatedTourLog.getTimeStamp(), updatedTourLog.getComment(),
                updatedTourLog.getDifficulty(), updatedTourLog.getTotalTimeMinutes(),
                updatedTourLog.getRating(), parentTour);

        newTourLog.setId(updatedTourLog.getId());

        this.tourLogRepository.saveAndFlush(newTourLog);

        //update tour calculated values, we have to refetch the tour to get the new logs
        this.tourService.updateCalculatedValues(parentTour.getId(), this.getAllByTourId(parentTour.getId()));

        return this.tourService.getById(parentTour.getId()).orElseThrow(
                () -> {
                    Logging.logger.error("Error updating the tour log with id [{}], the associated tour with id [{}] could not be found in the database", updatedTourLog.getId(), parentTour.getId());
                    return new RecordCreationErrorExc("Could not find the associated tour");
                }
        );
    }

    @Override
    @Transactional
    public Tour deleteTourLog(Long tourLogId) {

        TourLog tourLogToDelete = this.tourLogRepository.findById(tourLogId).orElseThrow(
                () -> {
                    Logging.logger.error("Tour log to delete with id [{}] is not present in the database", tourLogId);
                    return new RecordNotFoundExc("Could not find tour log with id: " + tourLogId);
                }
        );

        Long associatedTourId = tourLogToDelete.getTourId();

        this.tourLogRepository.deleteById(tourLogToDelete.getId());

        this.tourLogRepository.flush();

        List<TourLog> associatedTourLogs = this.tourLogRepository.findByTour_id(associatedTourId);

        return this.tourService.updateCalculatedValues(associatedTourId, associatedTourLogs);

    }
}
