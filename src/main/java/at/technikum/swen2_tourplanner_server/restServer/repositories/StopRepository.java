package at.technikum.swen2_tourplanner_server.restServer.repositories;

import at.technikum.swen2_tourplanner_server.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopRepository extends JpaRepository<Location, Long> {

}
