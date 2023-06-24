package at.technikum.swen2_tourplanner_server.restServer.controllers;

import at.technikum.swen2_tourplanner_server.Logging;
import at.technikum.swen2_tourplanner_server.dto.requests.TourRequestModel;
import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.RecordNotFoundExc;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourRepository;
import at.technikum.swen2_tourplanner_server.restServer.services.TourService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tour")
public class TourController extends Logging {

    private final TourService tourService;

    TourController(TourRepository tourRepository) {
        this.tourService = new TourService(tourRepository);
    }

    //Registers the entry points of the REST SERVER

    //region Get Routes
    @GetMapping("")
    List<Tour> all() {
        return tourService.getAll();
    }

    @GetMapping("/{id}")
    Tour getTour(@PathVariable Long id) {
        return tourService.getById(id).orElseThrow(
                () -> new RecordNotFoundExc("tour not found")
        );
    }

    @GetMapping("/export/{id}")
    String exportTour(@PathVariable Long id) {
        return tourService.exportTour(id);
    }
    //endregion

    //region Post Routes
    @PostMapping(value = "")
    Tour createTour(@RequestBody TourRequestModel tourRequestModel) {

            return this.tourService.createTour(tourRequestModel);

    }

    @PostMapping("/import")
    void importTour(@RequestBody Tour newTour) {
        tourService.importTour(newTour);
    }
    //endregion

    //region Put Routes
    @PutMapping(value = "")
    Tour updateTour(@RequestBody TourRequestModel tourRequestModel) {

            return tourService.updateTour(tourRequestModel);

    }
    //endregion

    //region Delete Routes
    @DeleteMapping("/{id}")
    void deleteTour(@PathVariable Long id) {

        this.logger.info("Received delete tour req for tour id: " + id);
        tourService.deleteTour(id);

    }
    //endregion

}
