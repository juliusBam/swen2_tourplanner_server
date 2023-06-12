package at.technikum.swen2_tourplanner_server.controllers;

import at.technikum.swen2_tourplanner_server.Logging;
import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.exceptions.TourNotFoundExc;
import at.technikum.swen2_tourplanner_server.repositories.TourRepository;
import at.technikum.swen2_tourplanner_server.services.TourService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tour")
public class TourController extends Logging {

    private final TourService tourServiceCreate;

    TourController(TourRepository tourRepository) {
        this.tourServiceCreate = new TourService(tourRepository);
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
    Long createTour(@RequestBody @Valid Tour newTour) {
        this.logger.info("Received create tour req with name: " + newTour.getName());
        return tourServiceCreate.createTour(newTour);
    }

    @PostMapping("/import")
    void importTour(@RequestBody Tour newTour) {
        this.logger.info("Received import tour req with name: " + newTour.getName());
        tourServiceCreate.importTour(newTour);
    }
    //endregion

    //region Put Routes
    @PutMapping("")
    Long updateTour(@RequestBody Tour updatedTour) {
        this.logger.info("Received put tour req for tour id: " + updatedTour.getId());
        return tourServiceCreate.updateTour(updatedTour);
    }
    //endregion

    //region Delete Routes
    @DeleteMapping("/{id}")
    void deleteTour(@PathVariable Long id) {
        this.logger.info("Received delete tour req for tour id: " + id);
        tourServiceCreate.deleteTour(id);
    }
    //endregion

}
