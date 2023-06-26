package at.technikum.swen2_tourplanner_server.restServer.services.interfaces;

import at.technikum.swen2_tourplanner_server.dto.TourRequestModel;
import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.entities.TourLog;

import java.util.List;
import java.util.Optional;

public interface ITourService {

    public Tour updateTour(TourRequestModel tourRequestModel);

    public void deleteTour(Long id);

    public void importTour(Tour newTour);

    public String exportTour(Long id);

    public List<Tour> getAll();

    public Optional<Tour> getById(Long id);

    public Tour createTour(TourRequestModel tourRequestModel);

    public Tour updateCalculatedValues(Long tourToUpdateId, List<TourLog> linkedLogs);
}
