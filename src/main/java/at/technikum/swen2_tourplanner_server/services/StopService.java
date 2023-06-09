package at.technikum.swen2_tourplanner_server.services;

import at.technikum.swen2_tourplanner_server.entities.Stop;
import at.technikum.swen2_tourplanner_server.repositories.StopRepository;
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
    public Stop createStop(Stop newStop) {
        return this.stopRepository.save(newStop);
    }

}
