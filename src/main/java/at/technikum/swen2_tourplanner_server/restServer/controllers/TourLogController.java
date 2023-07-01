package at.technikum.swen2_tourplanner_server.restServer.controllers;

import at.technikum.swen2_tourplanner_server.Logging;
import at.technikum.swen2_tourplanner_server.dto.responses.TourLogFetchResponseDto;
import at.technikum.swen2_tourplanner_server.dto.responses.TourLogManipulationResponseDto;
import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.entities.TourLog;
import at.technikum.swen2_tourplanner_server.dto.TourLogDto;
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
    List<TourLogDto> getTourLogByTourId(@PathVariable Long tourId) {
        return tourLogService.getAllByTourId(tourId);
    }
    //endregion

    //region Post Routes
    @PostMapping("")
    TourLogManipulationResponseDto createTourLog(@RequestBody TourLogDto newTourLog) {
        return tourLogService.createTourLog(newTourLog);
    }
    //endregion

    //region Put Routes
    @PutMapping("")
    TourLogManipulationResponseDto updateTourLog(@RequestBody TourLogDto updatedTourLog) {
        return tourLogService.updateTourLog(updatedTourLog);
    }
    //endregion

    //region Delete Routes
    @DeleteMapping("/{id}")
    TourLogManipulationResponseDto deleteTourLog(@PathVariable Long id) {
        return this.tourLogService.deleteTourLog(id);
    }
    //endregion

}

