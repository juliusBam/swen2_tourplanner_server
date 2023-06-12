package at.technikum.swen2_tourplanner_server.restServer.services;

import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.entities.TourLog;
import at.technikum.swen2_tourplanner_server.dto.requests.CreateTourLogReqModel;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourLogRepository;
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

    public Long createTourLog(CreateTourLogReqModel newTourLogReq) {

        Long linkedTourId = newTourLogReq.getTourId();

        Tour linkedTour = this.tourService.getById(linkedTourId).orElseThrow();

        TourLog newTourLog = new TourLog(newTourLogReq.getTimeStamp(), newTourLogReq.getComment(),
                                            newTourLogReq.getDifficulty(), newTourLogReq.getTotalTimeMinutes(),
                                            newTourLogReq.getRating(), linkedTour);

        return this.tourLogRepository.save(newTourLog).getId();
    }

    public Long updateTourLog(TourLog updatedTourLog) {

        //if tour log is not present anymore do not allow an update
        this.tourLogRepository.findById(updatedTourLog.getId()).orElseThrow();

        return this.tourLogRepository.save(updatedTourLog).getId();

    }

    public void deleteTourLog(Long tourId) {
        this.tourLogRepository.deleteById(tourId);
    }
}
