package at.technikum.swen2_tourplanner_server.restServer.repositories;

import at.technikum.swen2_tourplanner_server.entities.TourLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TourLogRepository extends JpaRepository<TourLog, Long> {
    List<TourLog> findByTour_id(Long id);

}
