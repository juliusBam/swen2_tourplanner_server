package at.technikum.swen2_tourplanner_server.repositories;

import at.technikum.swen2_tourplanner_server.entities.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TourRepository extends JpaRepository<Tour, Long> {

    //todo add query to join the tour logs

}
