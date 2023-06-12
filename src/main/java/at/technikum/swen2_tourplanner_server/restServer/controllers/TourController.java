package at.technikum.swen2_tourplanner_server.restServer.controllers;

import at.technikum.swen2_tourplanner_server.Logging;
import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.exceptions.TourNotFoundExc;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourLogRepository;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourRepository;
import at.technikum.swen2_tourplanner_server.restServer.services.TourService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/tour")
public class TourController extends Logging {

    private final TourService tourService;

    TourController(TourRepository tourRepository, TourLogRepository tourLogRepository) {
        this.tourService = new TourService(tourRepository, tourLogRepository);
    }

    //Register the entry points of the REST SERVER

    //region Get Routes
    @GetMapping("")
    List<Tour> all() {
        return tourService.getAll();
    }

    @GetMapping("/{id}")
    Tour getTour(@PathVariable Long id) {
        return tourService.getById(id).orElseThrow(
                () -> new TourNotFoundExc("tour not found")
        );
    }

    @GetMapping("/export/{id}")
    String exportTour(@PathVariable Long id) {
        return tourService.exportTour(id);
    }
    //endregion

    //region Post Routes
    //https://stackoverflow.com/questions/52818107/how-to-send-the-multipart-file-and-json-data-to-spring-boot
    //https://stackoverflow.com/questions/52818107/how-to-send-the-multipart-file-and-json-data-to-spring-boot
    //Long createTour(@RequestBody @Valid Tour newTour) {
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Long createTour(@RequestParam("formData") String tourRequestAsString,
                    @RequestParam("tourImage") MultipartFile tourImage) {

        return this.tourService.createTour(tourRequestAsString, tourImage);
    }

    @PostMapping("/import")
    void importTour(@RequestBody Tour newTour) {
        this.logger.info("Received import tour req with name: " + newTour.getName());
        tourService.importTour(newTour);
    }
    //endregion

    //region Put Routes

    @PutMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Long updateTour(@RequestParam(name = "formData") String tourRequestAsString,
                    @RequestParam(name = "tourImage") MultipartFile tourImage) {
        return tourService.updateTour(tourRequestAsString, tourImage);
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
