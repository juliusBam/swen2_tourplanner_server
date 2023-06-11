package at.technikum.swen2_tourplanner_server.controllers;

import at.technikum.swen2_tourplanner_server.Logging;
import at.technikum.swen2_tourplanner_server.entities.TourLog;
import at.technikum.swen2_tourplanner_server.models.requests.CreateTourLogReq;
import at.technikum.swen2_tourplanner_server.repositories.TourLogRepository;
import at.technikum.swen2_tourplanner_server.repositories.TourRepository;
import at.technikum.swen2_tourplanner_server.services.TourLogService;
import at.technikum.swen2_tourplanner_server.services.TourServiceBase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tourlog")
public class TourLogController extends Logging {

    private final TourLogService tourLogService;


    TourLogController(TourLogRepository tourLogRepository, TourRepository tourRepository) {
        this.tourLogService = new TourLogService(tourLogRepository, new TourServiceBase(tourRepository));
    }

    //Register the entry points of the REST SERVER

    //region Get Routes
    @GetMapping("/{tourId}")
    List<TourLog> getTourLogByTourId(@PathVariable Long tourId) {
        return tourLogService.getAllByTourId(tourId);
    }
    //endregion

    //region Post Routes
    @PostMapping("")
    Long createTourLog(@RequestBody CreateTourLogReq newTourLog) {
        this.logger.info("Received post req for new tour log for tour id: " + newTourLog.getTourId());
        return tourLogService.createTourLog(newTourLog);
    }
    //endregion

}

