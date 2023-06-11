package at.technikum.swen2_tourplanner_server.services;

import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.entities.TourLog;
import at.technikum.swen2_tourplanner_server.models.requests.CreateTourLogReq;
import at.technikum.swen2_tourplanner_server.repositories.TourLogRepository;
import at.technikum.swen2_tourplanner_server.repositories.TourRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourLogService {

    private final TourLogRepository tourLogRepository;
    private final TourServiceBase tourServiceBase;

    public TourLogService(TourLogRepository tourLogRepository, TourServiceBase tourServiceBase) {
        this.tourLogRepository = tourLogRepository;
        this.tourServiceBase = tourServiceBase;
    }

    public List<TourLog> getAll() {
        return this.tourLogRepository.findAll();
    }

    public List<TourLog> getAllByTourId(Long id) {

        return this.tourLogRepository.findTourLogsByTourId(id);
    }

    public Long createTourLog(CreateTourLogReq newTourLogReq) {

        Long linkedTourId = newTourLogReq.getTourId();

        Tour linkedTour = this.tourServiceBase.getById(linkedTourId).orElseThrow();

        TourLog newTourLog = new TourLog(newTourLogReq.getTimeStamp(), newTourLogReq.getComment(),
                                            newTourLogReq.getDifficulty(), newTourLogReq.getTotalTimeMinutes(),
                                            newTourLogReq.getRating(), linkedTour);

        return this.tourLogRepository.save(newTourLog).getId();
    }
}
