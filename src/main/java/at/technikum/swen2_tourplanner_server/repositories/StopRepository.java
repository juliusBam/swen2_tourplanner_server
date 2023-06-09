package at.technikum.swen2_tourplanner_server.repositories;

import at.technikum.swen2_tourplanner_server.entities.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopRepository extends JpaRepository<Stop, Long> {

}
