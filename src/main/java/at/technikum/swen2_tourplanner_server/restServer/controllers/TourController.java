package at.technikum.swen2_tourplanner_server.restServer.controllers;

import at.technikum.swen2_tourplanner_server.Logging;
import at.technikum.swen2_tourplanner_server.dto.TourDto;
import at.technikum.swen2_tourplanner_server.dto.responses.TourResponseDto;
import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.RecordNotFoundExc;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourRepository;
import at.technikum.swen2_tourplanner_server.restServer.services.TourService;
import at.technikum.swen2_tourplanner_server.restServer.services.interfaces.ITourService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tour")
public class TourController extends Logging {

    private final ITourService tourService;

    TourController(TourRepository tourRepository) {
        this.tourService = new TourService(tourRepository);
    }

    //Registers the entry points of the REST SERVER

    //region Get Routes
    @GetMapping("")
    List<TourResponseDto> all() {
        return tourService.getAll();
    }

    @GetMapping("/{id}")
    TourResponseDto getTour(@PathVariable Long id) {
        return tourService.getById(id);
    }

    @GetMapping("/export/{id}")
    String exportTour(@PathVariable Long id) {
        return tourService.exportTour(id);
    }
    //endregion

    //region Post Routes
    @PostMapping(value = "")
    TourResponseDto createTour(@RequestBody TourDto tourDto) {

            return this.tourService.createTour(tourDto);

    }

    @PostMapping("/import")
    void importTour(@RequestBody Tour newTour) {
        tourService.importTour(newTour);
    }
    //endregion

    //region Put Routes
    @PutMapping(value = "")
    TourResponseDto updateTour(@RequestBody TourDto tourDto) {

            return tourService.updateTour(tourDto);

    }
    //endregion

    //region Delete Routes
    @DeleteMapping("/{id}")
    void deleteTour(@PathVariable Long id) {

        tourService.deleteTour(id);

    }
    //endregion

}
