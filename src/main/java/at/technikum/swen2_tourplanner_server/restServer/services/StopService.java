package at.technikum.swen2_tourplanner_server.restServer.services;

import at.technikum.swen2_tourplanner_server.entities.Location;
import at.technikum.swen2_tourplanner_server.restServer.repositories.StopRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class StopService {

    private final StopRepository stopRepository;

    public StopService(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }

    @Transactional
    public Location createStop(Location newLocation) {
        return this.stopRepository.save(newLocation);
    }

}
