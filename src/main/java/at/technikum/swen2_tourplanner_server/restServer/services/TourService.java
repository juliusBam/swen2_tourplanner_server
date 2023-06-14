package at.technikum.swen2_tourplanner_server.restServer.services;

import at.technikum.swen2_tourplanner_server.dto.requests.CreateTourRequestModel;
import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.entities.TourLog;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.RecordNotFoundExc;
import at.technikum.swen2_tourplanner_server.helpers.validators.IValidator;
import at.technikum.swen2_tourplanner_server.helpers.validators.TourValidator;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TourService {
    private final TourRepository tourRepository;

    private final IValidator<CreateTourRequestModel> tourValidator;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
        this.tourValidator = new TourValidator();
    }

    public Long updateTour(String tourRequestModelString, MultipartFile tourImage) throws IOException, JsonProcessingException {

        CreateTourRequestModel createTourRequestModel = new ObjectMapper().readValue(tourRequestModelString, CreateTourRequestModel.class);

        this.tourValidator.validateUpdate(createTourRequestModel);

        Tour existingTour = this.tourRepository.findById(createTourRequestModel.getId()).orElseThrow(
                () -> new RecordNotFoundExc("could not find tour with name: " + createTourRequestModel.getName())
        );

        byte[] tourImagesAsBytes = tourImage.getBytes();

        Tour updatedTour = new Tour(
                createTourRequestModel.getName(),
                createTourRequestModel.getDescription(),
                createTourRequestModel.getVehicle(),
                createTourRequestModel.getStart(),
                createTourRequestModel.getEnd(),
                createTourRequestModel.getEstimatedTimeSeconds(),
                createTourRequestModel.getTourDistanceKilometers(),
                tourImagesAsBytes,
                existingTour.getLogs()
        );

        updatedTour.setId(createTourRequestModel.getId());

        return this.tourRepository.save(updatedTour).getId();
    }

    public void deleteTour(Long id) {
        this.tourRepository.deleteById(id);
    }

    public void importTour(Tour newTour) {
        //TODO parse the json into a new tour
        this.tourRepository.save(newTour);
    }

    public String exportTour(Long id) {
        Optional<Tour> tour = this.tourRepository.findById(id);
        //TODO parse tour into json file and send it back
        return tour.toString();
    }

    public List<Tour> getAll() {
        return this.tourRepository.findAll();
    }

    public Optional<Tour> getById(Long id) {
        return this.tourRepository.findById(id);
    }

    @Transactional
    public Long createTour(String tourRequestModelString, MultipartFile tourImage) throws JsonProcessingException, IOException {

        CreateTourRequestModel createTourRequestModel = new ObjectMapper().readValue(tourRequestModelString, CreateTourRequestModel.class);

        this.tourValidator.validateCreation(createTourRequestModel);

        byte[] tourImagesAsBytes = tourImage.getBytes();

        Tour newTour = new Tour(
                createTourRequestModel.getName(),
                createTourRequestModel.getDescription(),
                createTourRequestModel.getVehicle(),
                createTourRequestModel.getStart(),
                createTourRequestModel.getEnd(),
                createTourRequestModel.getEstimatedTimeSeconds(),
                createTourRequestModel.getTourDistanceKilometers(),
                tourImagesAsBytes,
                null
        );

        return this.tourRepository.save(newTour).getId();
    }

    public void updateCalculatedValues(Long tourToUpdateId, List<TourLog> linkedLogs) {

        Tour tourToUpdate = this.tourRepository.findById(tourToUpdateId).orElseThrow(
                () -> new RecordNotFoundExc("Could not find tour with id: " + tourToUpdateId)
        );

        int numberOfReviews = linkedLogs.size();

        Double childF = 0.0;

        for (TourLog tourlog: linkedLogs) {
            Double sum = (double)(tourlog.getDifficulty().ordinal() + tourlog.getRating().ordinal());

            if (tourlog.getTotalTimeMinutes() > 90) {
                sum = sum/2;
            }

            childF += sum;
        }

        if (tourToUpdate.getTourDistanceKilometers() > 150) {
            childF *= 0.7;
        }

        childF /= numberOfReviews;

        tourToUpdate.setPopularity(numberOfReviews);
        tourToUpdate.setChildFriendliness(childF.intValue());

        this.tourRepository.save(tourToUpdate);

    }
}
