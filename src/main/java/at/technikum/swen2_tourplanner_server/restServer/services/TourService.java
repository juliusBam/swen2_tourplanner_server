package at.technikum.swen2_tourplanner_server.restServer.services;

import at.technikum.swen2_tourplanner_server.dto.requests.TourRequestModel;
import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.entities.TourLog;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.RecordCreationErrorExc;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.RecordNotFoundExc;
import at.technikum.swen2_tourplanner_server.BL.validators.IValidator;
import at.technikum.swen2_tourplanner_server.BL.validators.TourValidator;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourService {
    private final TourRepository tourRepository;

    private final IValidator<TourRequestModel> tourValidator;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
        this.tourValidator = new TourValidator();
    }

    @Transactional
    public Tour updateTour(TourRequestModel tourRequestModel) {

        this.tourValidator.validateUpdate(tourRequestModel);

        Tour existingTour = this.tourRepository.findById(tourRequestModel.getId()).orElseThrow(
                () -> new RecordNotFoundExc("could not find tour with name: " + tourRequestModel.getName())
        );

        Tour updatedTour = new Tour(
                tourRequestModel.getName(),
                tourRequestModel.getDescription(),
                tourRequestModel.getVehicle(),
                tourRequestModel.getStart(),
                tourRequestModel.getEnd(),
                tourRequestModel.getEstimatedTimeSeconds(),
                tourRequestModel.getTourDistanceKilometers(),
                tourRequestModel.getRouteInformation(),
                existingTour.getLogs()
        );

        updatedTour.setId(tourRequestModel.getId());

        Long insertedId = this.tourRepository.saveAndFlush(updatedTour).getId();

        return this.updateCalculatedValues(insertedId, updatedTour.getLogs());

    }

    @Transactional
    public void deleteTour(Long id) {

        if (this.tourRepository.findById(id).isPresent()) {

            this.tourRepository.deleteById(id);

        } else {

            throw new RecordNotFoundExc("Could not find tour with id: " + id);

        }

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
    public Tour createTour(TourRequestModel tourRequestModel) {

        if (tourRequestModel.getId() != null) {
            throw new RecordCreationErrorExc("Tour id cannot be set on creation");
        }

        this.tourValidator.validateCreation(tourRequestModel);

        Tour newTour = new Tour(
                tourRequestModel.getName(),
                tourRequestModel.getDescription(),
                tourRequestModel.getVehicle(),
                tourRequestModel.getStart(),
                tourRequestModel.getEnd(),
                tourRequestModel.getEstimatedTimeSeconds(),
                tourRequestModel.getTourDistanceKilometers(),
                tourRequestModel.getRouteInformation(),
                null
        );

        Long addedId = this.tourRepository.saveAndFlush(newTour).getId();
        
        Tour addedTour = this.tourRepository.findById(addedId).orElseThrow(
                () -> new RecordCreationErrorExc("The tour could not be created")
        );
        return addedTour;
    }

    @Transactional
    public Tour updateCalculatedValues(Long tourToUpdateId, List<TourLog> linkedLogs) {

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
        tourToUpdate.setChildFriendliness(childF);

        this.tourRepository.save(tourToUpdate);

        return tourToUpdate;
    }
}
