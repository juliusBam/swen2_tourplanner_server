package at.technikum.swen2_tourplanner_server.services;

import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.entities.TourLog;
import at.technikum.swen2_tourplanner_server.dto.requests.CreateTourLogReq;
import at.technikum.swen2_tourplanner_server.repositories.TourLogRepository;
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

    public List<TourLog> getAll() {
        return this.tourLogRepository.findAll();
    }

    public List<TourLog> getAllByTourId(Long id) {

        return this.tourLogRepository.findTourLogsByTourId(id);
    }

    public Long createTourLog(CreateTourLogReq newTourLogReq) {

        Long linkedTourId = newTourLogReq.getTourId();

        Tour linkedTour = this.tourService.getById(linkedTourId).orElseThrow();

        TourLog newTourLog = new TourLog(newTourLogReq.getTimeStamp(), newTourLogReq.getComment(),
                                            newTourLogReq.getDifficulty(), newTourLogReq.getTotalTimeMinutes(),
                                            newTourLogReq.getRating(), linkedTour);

        return this.tourLogRepository.save(newTourLog).getId();
    }
}
