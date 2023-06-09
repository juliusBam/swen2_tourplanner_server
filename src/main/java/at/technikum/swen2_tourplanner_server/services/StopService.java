package at.technikum.swen2_tourplanner_server.services;

import at.technikum.swen2_tourplanner_server.entities.Stop;
import at.technikum.swen2_tourplanner_server.repositories.StopRepository;

public class StopService {

    private final StopRepository stopRepository;

    public StopService(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }

    public Stop createStop(Stop newStop) {
        return this.stopRepository.save(newStop);
    }

}
