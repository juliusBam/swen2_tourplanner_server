package at.technikum.swen2_tourplanner_server.restServer.services;

import at.technikum.swen2_tourplanner_server.dto.requests.CreateTourRequestModel;
import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.entities.TourLog;
import at.technikum.swen2_tourplanner_server.exceptions.TourCreationErrorExc;
import at.technikum.swen2_tourplanner_server.exceptions.TourUpdateErrorExc;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourLogRepository;
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

    private final TourLogService tourLogService;

    public TourService(TourRepository tourRepository, TourLogRepository tourLogRepository) {
        this.tourRepository = tourRepository;
        this.tourLogService = new TourLogService(tourLogRepository, this);
    }

    public Long updateTour(String tourRequestModelString, MultipartFile tourImage) {

        //todo group the validation together

        CreateTourRequestModel createTourRequestModel;

        try {
            createTourRequestModel = new ObjectMapper().readValue(tourRequestModelString, CreateTourRequestModel.class);
        } catch (JsonProcessingException e) {
            throw new TourCreationErrorExc(e.getMessage());
        }

        if (createTourRequestModel == null) {
            throw new TourCreationErrorExc("Tour model is null");
        }

        if (createTourRequestModel.getId() == null) {
            throw new TourUpdateErrorExc("Tour id has to be set");
        }

        if (createTourRequestModel.getStart().getCoordinate().equals(createTourRequestModel.getEnd().getCoordinate())) {
            throw new TourCreationErrorExc("Tour start and tour end have to be different");
        }

        byte[] tourImagesAsBytes;

        try {
            tourImagesAsBytes = tourImage.getBytes();
        } catch (IOException e) {
            throw new TourCreationErrorExc("Error parsing the tour image");
        }

        //todo add error msg
        this.tourRepository.findById(createTourRequestModel.getId()).orElseThrow();

        List<TourLog> relatedLogs = this.tourLogService.getAllByTourId(createTourRequestModel.getId());

        Tour updatedTour = new Tour(
                createTourRequestModel.getName(),
                createTourRequestModel.getDescription(),
                createTourRequestModel.getVehicle(),
                createTourRequestModel.getStart(),
                createTourRequestModel.getEnd(),
                createTourRequestModel.getEstimatedTimeSeconds(),
                createTourRequestModel.getTourDistanceKilometers(),
                tourImagesAsBytes,
                relatedLogs
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
        Tour tour = this.tourRepository.getById(id);
        //TODO parse tour into json
        return tour.toString();
    }

    public List<Tour> getAll() {
        return this.tourRepository.findAll();
    }

    public Optional<Tour> getById(Long id) {
        return this.tourRepository.findById(id);
    }

    @Transactional
    public Long createTour(String tourRequestModelString, MultipartFile tourImage) {

        CreateTourRequestModel createTourRequestModel;

        try {
            createTourRequestModel = new ObjectMapper().readValue(tourRequestModelString, CreateTourRequestModel.class);
        } catch (JsonProcessingException e) {
            throw new TourCreationErrorExc(e.getMessage());
        }

        if (createTourRequestModel == null) {
            throw new TourCreationErrorExc("Tour model is null");
        }

        if (createTourRequestModel.getStart().getCoordinate().equals(createTourRequestModel.getEnd().getCoordinate())) {
            throw new TourCreationErrorExc("Tour start and tour end have to be different");
        }

        byte[] tourImagesAsBytes;

        try {
            tourImagesAsBytes = tourImage.getBytes();
        } catch (IOException e) {
            throw new TourCreationErrorExc("Error parsing the tour image");
        }

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
}
