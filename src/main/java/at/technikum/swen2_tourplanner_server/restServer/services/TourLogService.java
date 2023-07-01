package at.technikum.swen2_tourplanner_server.restServer.services;

import at.technikum.swen2_tourplanner_server.BL.TourLogModelConverter;
import at.technikum.swen2_tourplanner_server.BL.TourModelConverter;
import at.technikum.swen2_tourplanner_server.Logging;
import at.technikum.swen2_tourplanner_server.dto.TourStatsDto;
import at.technikum.swen2_tourplanner_server.dto.responses.TourLogFetchResponseDto;
import at.technikum.swen2_tourplanner_server.dto.responses.TourLogManipulationResponseDto;
import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.entities.TourLog;
import at.technikum.swen2_tourplanner_server.dto.TourLogDto;
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
    public List<TourLogDto> getAllByTourId(Long id) {

        List<TourLog> tourLogs = this.tourLogRepository.findByTour_id(id);

        return tourLogs.stream().map(TourLogModelConverter::tourLogEntityToDto).toList();
    }

    @Override
    @Transactional
    public TourLogManipulationResponseDto createTourLog(TourLogDto newTourLogReq) {

        Long linkedTourId = newTourLogReq.getTourId();

        Tour linkedTour = this.tourService.getByIdEntityModel(linkedTourId).orElseThrow(
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

        TourLog createdLog = this.tourLogRepository.saveAndFlush(newTourLog);

        //todo calculate stats
        return new TourLogManipulationResponseDto(
                TourLogModelConverter.tourLogEntityToDto(createdLog),
                new TourStatsDto(
                        Integer.valueOf(0),
                        0D,
                        0D,
                        0D,
                        0D
                )
        );

    }

    @Override
    @Transactional
    public TourLogManipulationResponseDto updateTourLog(TourLogDto updatedTourLog) {

        //if tour log is not present anymore do not allow an update
        this.tourLogRepository.findById(updatedTourLog.getId()).orElseThrow(
                () -> {
                    Logging.logger.error("Error updating the tour log with id [{}], the tour log could not be found in the database", updatedTourLog.getId());
                    return new RecordNotFoundExc("Tour log not found");
                }
        );

        Tour parentTour = this.tourService.getByIdEntityModel(updatedTourLog.getTourId()).orElseThrow();

        TourLog newTourLog = new TourLog(updatedTourLog.getTimeStamp(), updatedTourLog.getComment(),
                updatedTourLog.getDifficulty(), updatedTourLog.getTotalTimeMinutes(),
                updatedTourLog.getRating(), parentTour);

        newTourLog.setId(updatedTourLog.getId());

        TourLog createdLog = this.tourLogRepository.saveAndFlush(newTourLog);

        //todo calculate stats
        return new TourLogManipulationResponseDto(
                TourLogModelConverter.tourLogEntityToDto(createdLog),
                new TourStatsDto(
                        Integer.valueOf(0),
                        0D,
                        0D,
                        0D,
                        0D
                )
        );

    }

    @Override
    @Transactional
    public TourLogManipulationResponseDto deleteTourLog(Long tourLogId) {

        TourLog tourLogToDelete = this.tourLogRepository.findById(tourLogId).orElseThrow(
                () -> {
                    Logging.logger.error("Tour log to delete with id [{}] is not present in the database", tourLogId);
                    return new RecordNotFoundExc("Could not find tour log with id: " + tourLogId);
                }
        );

        this.tourLogRepository.deleteById(tourLogToDelete.getId());

        this.tourLogRepository.flush();

        //todo calculate stats
        TourLogManipulationResponseDto tourLogManipulationResponseDto = new TourLogManipulationResponseDto();
        tourLogManipulationResponseDto.setTourStats(new TourStatsDto(
                Integer.valueOf(0),
                0D,
                0D,
                0D,
                0D
        ));

        return tourLogManipulationResponseDto;

    }
}
