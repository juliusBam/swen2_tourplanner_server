package at.technikum.swen2_tourplanner_server.restServer.services;

import at.technikum.swen2_tourplanner_server.BL.StatsCalculator;
import at.technikum.swen2_tourplanner_server.BL.TourModelConverter;
import at.technikum.swen2_tourplanner_server.BL.model.AverageStatsModel;
import at.technikum.swen2_tourplanner_server.BL.model.ReportInputData;
import at.technikum.swen2_tourplanner_server.BL.model.TourStatsModel;
import at.technikum.swen2_tourplanner_server.Logging;
import at.technikum.swen2_tourplanner_server.dto.TourDto;
import at.technikum.swen2_tourplanner_server.dto.TourStatsDto;
import at.technikum.swen2_tourplanner_server.dto.responses.TourResponseDto;
import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.RecordCreationErrorExc;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.RecordNotFoundExc;
import at.technikum.swen2_tourplanner_server.BL.validators.IValidator;
import at.technikum.swen2_tourplanner_server.BL.validators.TourValidator;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.TourStatsException;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourRepository;
import at.technikum.swen2_tourplanner_server.restServer.services.interfaces.ITourService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TourService extends Logging implements ITourService {
    private final TourRepository tourRepository;

    private final IValidator<TourDto> tourValidator;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
        this.tourValidator = new TourValidator();
    }

    @Override
    @Transactional
    public TourResponseDto updateTour(TourDto tourDto) {

        this.tourValidator.validateUpdate(tourDto);

        Tour existingTour = this.tourRepository.findById(tourDto.getId()).orElseThrow(
                () -> {
                    Logging.logger.error("Tour to update with id [{}] and name [{}] is not present in the database"
                                            , tourDto.getId(), tourDto.getName());
                    return new RecordNotFoundExc("Could not find tour with name: " + tourDto.getName());
                }
        );

        Tour updatedTour = new Tour(
                tourDto.getName(),
                tourDto.getDescription(),
                tourDto.getVehicle(),
                tourDto.getStart(),
                tourDto.getEnd(),
                tourDto.getEstimatedTimeSeconds(),
                tourDto.getTourDistanceKilometers(),
                tourDto.getRouteInformation(),
                existingTour.getLogs()
        );

        updatedTour.setId(tourDto.getId());

        Tour addedTour = this.tourRepository.saveAndFlush(updatedTour);

        TourStatsModel tourStatsModel = this.calculateTourStats(addedTour);


        return new TourResponseDto(
                new TourStatsDto(
                        tourStatsModel.popularity(),
                        tourStatsModel.childFriendliness(),
                        tourStatsModel.avgTime(),
                        tourStatsModel.avgRating(),
                        tourStatsModel.avgDifficulty()
                ),
                TourModelConverter.tourEntitytoDto(addedTour)
        );

    }

    @Override
    @Transactional
    public void deleteTour(Long id) {

        if (this.tourRepository.findById(id).isPresent()) {

            this.tourRepository.deleteById(id);

        } else {

            Logging.logger.error("Tour to delete with id [{}] is not present in the database", id);
            throw new RecordNotFoundExc("Could not find tour with id: " + id);

        }

    }

    @Override
    public void importTour(Tour newTour) {
        //TODO parse the json into a new tour
        this.tourRepository.save(newTour);
    }

    @Override
    public String exportTour(Long id) {
        Optional<Tour> tour = this.tourRepository.findById(id);
        return tour.toString();
    }

    @Override
    public List<TourResponseDto> getAll() {
        List<Tour> tours = this.tourRepository.findAll();

        List<TourResponseDto> tourResponses = new ArrayList<>();

        //todo calculate stats
        for (Tour tour : tours) {

            TourStatsModel tourStatsModel = this.calculateTourStats(tour);

            tourResponses.add(
                    new TourResponseDto(
                            new TourStatsDto(
                                    tourStatsModel.popularity(),
                                    tourStatsModel.childFriendliness(),
                                    tourStatsModel.avgTime(),
                                    tourStatsModel.avgRating(),
                                    tourStatsModel.avgDifficulty()
                            ),
                            TourModelConverter.tourEntitytoDto(tour)
                    )
            );

        }

        return tourResponses;
    }

    @Override
    public TourResponseDto getById(Long id) {

        Tour tour = this.tourRepository.findById(id).orElseThrow(
                () -> {
                    Logging.logger.error("Tour to fetch with id [{}] is not present in the database", id);
                    throw new RecordNotFoundExc("Could not find tour with id: " + id);
                }
        );

        TourStatsModel tourStatsModel = this.calculateTourStats(tour);

        return new TourResponseDto(
                new TourStatsDto(
                        tourStatsModel.popularity(),
                        tourStatsModel.childFriendliness(),
                        tourStatsModel.avgTime(),
                        tourStatsModel.avgRating(),
                        tourStatsModel.avgDifficulty()
                ),
                TourModelConverter.tourEntitytoDto(tour)
        );
    }

    @Override
    public Optional<Tour> getByIdEntityModel(Long id) {
        return this.tourRepository.findById(id);
    }

    @Override
    @Transactional
    public TourResponseDto createTour(TourDto tourDto) {

        if (tourDto.getId() != null) {
            Logging.logger.error("Tour id is set on creation id [{}] and name [{}]", tourDto.getId(), tourDto.getName());
            throw new RecordCreationErrorExc("Tour id cannot be set on creation");
        }

        this.tourValidator.validateCreation(tourDto);

        Tour newTour = new Tour(
                tourDto.getName(),
                tourDto.getDescription(),
                tourDto.getVehicle(),
                tourDto.getStart(),
                tourDto.getEnd(),
                tourDto.getEstimatedTimeSeconds(),
                tourDto.getTourDistanceKilometers(),
                tourDto.getRouteInformation(),
                Collections.emptyList()
        );

        Long addedId = this.tourRepository.saveAndFlush(newTour).getId();
        
        Tour addedTour = this.tourRepository.findById(addedId).orElseThrow(
                () -> {
                    Logging.logger.error("The created tour with id [{}] and name [{}] could not be found in the database", addedId, tourDto.getName());
                    return new RecordCreationErrorExc("The tour could not be created");
                }
        );

        TourStatsModel tourStatsModel = this.calculateTourStats(addedTour);

        return new TourResponseDto(
                new TourStatsDto(
                        tourStatsModel.popularity(),
                        tourStatsModel.childFriendliness(),
                        tourStatsModel.avgTime(),
                        tourStatsModel.avgRating(),
                        tourStatsModel.avgDifficulty()
                ),
                TourModelConverter.tourEntitytoDto(addedTour)
        );
    }

    @Override
    public List<Tour> getAllEntityModel() {
        return this.tourRepository.findAll();
    }

    @Override
    public List<ReportInputData> getAllSummarizeReport() {
        List<Tour> tours = this.tourRepository.findAll();

        List<ReportInputData> toursReportInputData = new ArrayList<>();

        //todo calculate stats
        for (Tour tour : tours) {

            TourStatsModel tourStatsModel = this.calculateTourStats(tour);

            toursReportInputData.add(
                    new ReportInputData(
                            tour, new TourStatsModel(tourStatsModel.popularity(),
                            tourStatsModel.childFriendliness(),
                            tourStatsModel.avgTime(),
                            tourStatsModel.avgRating(),
                            tourStatsModel.avgDifficulty())
                    )
            );

        }

        return toursReportInputData;
    }

    /*@Override
    @Transactional
    public Tour updateCalculatedValues(Long tourToUpdateId, List<TourLog> linkedLogs) {

        Tour tourToUpdate = this.tourRepository.findById(tourToUpdateId).orElseThrow(
                () -> {
                    Logging.logger.error("Error updating the calculated values of tour with id [{}], could not find the tour", tourToUpdateId);
                    return new RecordNotFoundExc("Could not find tour with id: " + tourToUpdateId);
                }
        );

        int numberOfReviews = linkedLogs.size();

        Double childF = 0.0;

        for (TourLog tourlog : linkedLogs) {
            Double sum = (double) (tourlog.getDifficulty().ordinal() + tourlog.getRating().ordinal());

            if (tourlog.getTotalTimeMinutes() > 90) {
                sum = sum / 2;
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
    }*/

    public TourStatsModel calculateTourStats(Long tourId) {

        Tour tour = this.tourRepository.findById(tourId).orElseThrow(
                () -> {
                    logger.error(String.format("Could not find tour with id: [{0}] while calculating stats", tourId));
                    throw new TourStatsException("Could not find tour with id: [" + tourId + "]");
                }
        );

        return this.calculateTourStats(tour);
    }


    public TourStatsModel calculateTourStats(Tour tour) throws RuntimeException {

        StatsCalculator statsCalculator = new StatsCalculator();

        Integer popularity = this.calculatePopularity(tour);

        AverageStatsModel averageStatsModel = statsCalculator.calculateAverageStats(tour);

        return new TourStatsModel(
                popularity, averageStatsModel.childFriendliness(), averageStatsModel.avgTime(),
                averageStatsModel.avgRating(), averageStatsModel.avgDifficulty()
        );

    }

    private List<Tour> sortToursByPopularity() {

        List<Tour> tours = this.tourRepository.findAll();

        tours.sort((tour1, tour2) -> {
            if (tour1.getLogs().size() == tour2.getLogs().size())
                return 0;

            if (tour1.getLogs().size() > tour2.getLogs().size())
                return -1;

            return 1;
        });

        return tours;

    }

    private Integer calculatePopularity(Tour tour) throws RuntimeException {

        List<Tour> sortedTours = this.sortToursByPopularity();

        Optional<Tour> foundTour = sortedTours.stream().filter(tourInList -> tourInList.getId().equals(tour.getId())).findFirst();

        if (foundTour.isEmpty()) {
            logger.error(String.format("No tour found to calculate the popularity for, tour id [{0}]", tour.getId()));
            throw new TourStatsException("No tour found to calculate the popularity for");
        }

        return sortedTours.indexOf(foundTour.get()) + 1;

    }


}
