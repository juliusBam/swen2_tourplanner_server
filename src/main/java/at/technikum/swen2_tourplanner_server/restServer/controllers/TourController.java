package at.technikum.swen2_tourplanner_server.restServer.controllers;

import at.technikum.swen2_tourplanner_server.Logging;
import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.RecordCreationErrorExc;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.RecordNotFoundExc;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.RecordUpdateErrorExc;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourRepository;
import at.technikum.swen2_tourplanner_server.restServer.services.TourService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    //todo remove the fotos from the response
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

    //todo add getFotoByTourId


    //endregion

    //region Post Routes
    //https://stackoverflow.com/questions/52818107/how-to-send-the-multipart-file-and-json-data-to-spring-boot
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Long createTour(@RequestParam("formData") String tourRequestAsString,
                    @RequestParam("tourImage") MultipartFile tourImage) {

        try {

            return this.tourService.createTour(tourRequestAsString, tourImage);

        } catch (JsonProcessingException e) {

            this.logger.error("Error parsing tour creation request");
            throw new RecordCreationErrorExc(e.getMessage());

        } catch (IOException e) {

            this.logger.error("Error processing the image in the tour creation request");
            throw new RecordCreationErrorExc("Error parsing the tour image");

        }
}

    @PostMapping("/import")
    void importTour(@RequestBody Tour newTour) {
        tourService.importTour(newTour);
    }
    //endregion

    //region Put Routes

    @PutMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Long updateTour(@RequestParam(name = "formData") String tourRequestAsString,
                    @RequestParam(name = "tourImage") MultipartFile tourImage) {

        try {

            return tourService.updateTour(tourRequestAsString, tourImage);

        } catch (JsonProcessingException e) {

            this.logger.error("Error parsing tour update request");
            throw new RecordUpdateErrorExc(e.getMessage());

        } catch (IOException e) {

                this.logger.error("Error processing the image in the tour update request");
                throw new RecordUpdateErrorExc("Error parsing the tour image");

        }

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
