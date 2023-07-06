package at.technikum.swen2_tourplanner_server.BL;

import at.technikum.swen2_tourplanner_server.dto.TourDto;
import at.technikum.swen2_tourplanner_server.entities.Tour;

public class TourModelConverter {
    public static TourDto tourEntitytoDto(Tour tour) {

        return new TourDto(
                tour.getId(),
                tour.getName(),
                tour.getDescription(),
                tour.getVehicle(),
                tour.getFrom(),
                tour.getTo(),
                tour.getEstimatedTimeSeconds(),
                tour.getTourDistanceKilometers(),
                tour.getRouteInformation(),
                tour.getLogs().stream().map(TourLogModelConverter::tourLogEntityToDto).toList()
        );

    }
}
