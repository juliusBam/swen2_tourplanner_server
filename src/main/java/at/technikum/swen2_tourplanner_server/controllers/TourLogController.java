package at.technikum.swen2_tourplanner_server.controllers;

import at.technikum.swen2_tourplanner_server.entities.TourLog;
import at.technikum.swen2_tourplanner_server.repositories.TourLogRepository;
import at.technikum.swen2_tourplanner_server.services.TourLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tourlog")
public class TourLogController {

    private final TourLogService tourLogService;


    TourLogController(TourLogRepository tourLogRepository) {
        this.tourLogService = new TourLogService(tourLogRepository);
    }

    //Register the entry points of the REST SERVER

    //region Get Routes
    @GetMapping("/{id}")
    List<TourLog> getTour(@PathVariable Long id) {
        return tourLogService.getAllByTourId(id);
    }
    //endregion

    //region Post Routes
    @PostMapping("")
    Long createTourLog(@RequestBody TourLog newTourLog) {
        return tourLogService.createTourLog(newTourLog);
    }
    //endregion

}

