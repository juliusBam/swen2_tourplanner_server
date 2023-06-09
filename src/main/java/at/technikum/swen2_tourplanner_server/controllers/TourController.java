package at.technikum.swen2_tourplanner_server.controllers;

import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.exceptions.TourNotFoundExc;
import at.technikum.swen2_tourplanner_server.repositories.StopRepository;
import at.technikum.swen2_tourplanner_server.repositories.TourRepository;
import at.technikum.swen2_tourplanner_server.services.StopService;
import at.technikum.swen2_tourplanner_server.services.TourServiceCreate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tour")
public class TourController {

    private final TourServiceCreate tourServiceCreate;

    TourController(TourRepository tourRepository, StopRepository stopRepository) {
        this.tourServiceCreate = new TourServiceCreate(tourRepository, new StopService(stopRepository));
    }

    //Register the entry points of the REST SERVER

    //region Get Routes
    @GetMapping("")
    List<Tour> all() {
        return tourServiceCreate.getAll();
    }

    @GetMapping("/{id}")
    Tour getTour(@PathVariable Long id) {
        return tourServiceCreate.getById(id).orElseThrow(
                () -> new TourNotFoundExc("tour not found")
        );
    }

    @GetMapping("/export/{id}")
    String exportTour(@PathVariable Long id) {
        return tourServiceCreate.exportTour(id);
    }
    //endregion

    //region Post Routes
    @PostMapping("")
    Long createTour(@RequestBody Tour newTour) {
        return tourServiceCreate.createTour(newTour);
    }

    @PostMapping("/import")
    void importTour(@RequestBody Tour newTour) {
        tourServiceCreate.importTour(newTour);
    }
    //endregion

    //region Put Routes
    @PutMapping("")
    Long updateTour(@RequestBody Tour updatedTour) {
        return tourServiceCreate.updateTour(updatedTour);
    }
    //endregion

    //region Delete Routes
    @DeleteMapping("/{id}")
    void deleteTour(@PathVariable Long id) {
        tourServiceCreate.deleteTour(id);
    }
    //endregion

}
