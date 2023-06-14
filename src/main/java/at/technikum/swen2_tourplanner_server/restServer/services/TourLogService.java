package at.technikum.swen2_tourplanner_server.restServer.services;

import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.entities.TourLog;
import at.technikum.swen2_tourplanner_server.dto.requests.CreateTourLogReqModel;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.RecordCreationErrorExc;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.RecordNotFoundExc;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourLogRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourLogService {

    private final TourLogRepository tourLogRepository;
    private final TourService tourService;

    public TourLogService(TourLogRepository tourLogRepository, TourService tourService) {
        this.tourLogRepository = tourLogRepository;
        this.tourService = tourService;
    }

    public List<TourLog> getAllByTourId(Long id) {

        return this.tourLogRepository.findTourLogsByTourId(id);
    }

    @Transactional
    public Long createTourLog(CreateTourLogReqModel newTourLogReq) {

        Long linkedTourId = newTourLogReq.getTourId();

        Tour linkedTour = this.tourService.getById(linkedTourId).orElseThrow();

        TourLog newTourLog = new TourLog(newTourLogReq.getTimeStamp(), newTourLogReq.getComment(),
                                            newTourLogReq.getDifficulty(), newTourLogReq.getTotalTimeMinutes(),
                                            newTourLogReq.getRating(), linkedTour);

        linkedTour.addLog(newTourLog);

        Long newTourLogId = this.tourLogRepository.saveAndFlush(newTourLog).getId();

        //update tour calculated values, we have to refetch the tour to get the new logs
        this.tourService.updateCalculatedValues(linkedTour.getId(), this.getAllByTourId(linkedTour.getId()));

        return newTourLogId;
    }

    @Transactional
    public Long updateTourLog(CreateTourLogReqModel updatedTourLog) {

        //if tour log is not present anymore do not allow an update
        this.tourLogRepository.findById(updatedTourLog.getId()).orElseThrow(
                () -> new RecordNotFoundExc("Associated tour not found")
        );

        Tour parentTour = this.tourService.getById(updatedTourLog.getTourId()).orElseThrow();

        TourLog newTourLog = new TourLog(updatedTourLog.getTimeStamp(), updatedTourLog.getComment(),
                updatedTourLog.getDifficulty(), updatedTourLog.getTotalTimeMinutes(),
                updatedTourLog.getRating(), parentTour);

        newTourLog.setId(updatedTourLog.getId());

        this.tourLogRepository.saveAndFlush(newTourLog);

        //update tour calculated values  we have to refetch the tour to get the new logs
        this.tourService.updateCalculatedValues(parentTour.getId(), this.getAllByTourId(parentTour.getId()));

        return updatedTourLog.getId();
    }

    public void deleteTourLog(Long tourId) {
        this.tourLogRepository.deleteById(tourId);
    }
}
