package at.technikum.swen2_tourplanner_server.restServer.controllers;

import at.technikum.swen2_tourplanner_server.Logging;
import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.entities.TourLog;
import at.technikum.swen2_tourplanner_server.dto.TourLogReqModel;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourLogRepository;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourRepository;
import at.technikum.swen2_tourplanner_server.restServer.services.TourLogService;
import at.technikum.swen2_tourplanner_server.restServer.services.TourService;
import at.technikum.swen2_tourplanner_server.restServer.services.interfaces.ITourLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tourlog")
public class TourLogController extends Logging {

    private final ITourLogService tourLogService;

    TourLogController(TourLogRepository tourLogRepository, TourRepository tourRepository) {
        this.tourLogService = new TourLogService(tourLogRepository, new TourService(tourRepository));
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
    Tour createTourLog(@RequestBody TourLogReqModel newTourLog) {
        this.logger.info("Received post req for new tour log for tour id: " + newTourLog.getTourId());
        return tourLogService.createTourLog(newTourLog);
    }
    //endregion

    //region Put Routes
    @PutMapping("")
    Tour updateTourLog(@RequestBody TourLogReqModel updatedTourLog) {
        //this.logger.info();
        return tourLogService.updateTourLog(updatedTourLog);
    }
    //endregion

    //region Delete Routes
    @DeleteMapping("/{id}")
    Tour deleteTourLog(@PathVariable Long id) {
        return this.tourLogService.deleteTourLog(id);
    }
    //endregion

}

