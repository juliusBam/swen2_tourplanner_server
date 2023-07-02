package at.technikum.swen2_tourplanner_server.restServer.services.interfaces;

import at.technikum.swen2_tourplanner_server.BL.model.ReportInputData;
import at.technikum.swen2_tourplanner_server.BL.model.TourStatsModel;
import at.technikum.swen2_tourplanner_server.dto.TourDto;
import at.technikum.swen2_tourplanner_server.dto.responses.TourResponseDto;
import at.technikum.swen2_tourplanner_server.entities.Tour;

import java.util.List;
import java.util.Optional;

public interface ITourService {

    public TourResponseDto updateTour(TourDto tourDto);

    public void deleteTour(Long id);

    public void importTour(Tour newTour);

    public String exportTour(Long id);

    public List<TourResponseDto> getAll();

    public TourResponseDto getById(Long id);

    public Optional<Tour> getByIdEntityModel(Long id);

    public TourResponseDto createTour(TourDto tourDto);

    List<Tour> getAllEntityModel();

    List<ReportInputData> getAllSummarizeReport();

    public TourStatsModel calculateTourStats(Tour tour);

    public TourStatsModel calculateTourStats(Long tourId);

    //public Tour updateCalculatedValues(Long tourToUpdateId, List<TourLog> linkedLogs);
}
