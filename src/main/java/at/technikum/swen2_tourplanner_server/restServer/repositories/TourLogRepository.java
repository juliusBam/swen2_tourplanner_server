package at.technikum.swen2_tourplanner_server.restServer.repositories;

import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.entities.TourLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourLogRepository extends JpaRepository<TourLog, Long> {
    List<TourLog> findTourLogsByTourId(Long id);

}
