package at.technikum.swen2_tourplanner_server.services;

import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.entities.TourLog;
import at.technikum.swen2_tourplanner_server.repositories.TourLogRepository;

import java.util.List;

public class TourLogService {

    private final TourLogRepository tourLogRepository;

    public TourLogService(TourLogRepository tourLogRepository) {
        this.tourLogRepository = tourLogRepository;
    }

    public List<TourLog> getAll() {
        return this.tourLogRepository.findAll();
    }

    public List<TourLog> getAllByTourId(Long id) {
        return this.tourLogRepository.findByTourId(id);
    }

    public Long createTourLog(TourLog newTourLog) {
        return this.tourLogRepository.save(newTourLog).getId();
    }
}
