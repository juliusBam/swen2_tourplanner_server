package at.technikum.swen2_tourplanner_server.dto;

import at.technikum.swen2_tourplanner_server.entities.TourLog;
import at.technikum.swen2_tourplanner_server.entities.enums.Vehicle;

import java.util.List;

//todo add param for force update --> see version

public class TourRequestModel {

    //region fields
    private Long id;
    private String name;

    private String description;

    private String start;

    private String end;

    private Vehicle vehicle;

    private Long estimatedTimeSeconds;

    private Double tourDistanceKilometers;

    private String routeInformation;

    private List<TourLogReqModel> tourLogs;
    //endregion

    //region constructors
    public TourRequestModel() {}
    public TourRequestModel(Long id, String name, String description, Vehicle vehicle, String start, String end, Long estimatedTimeSeconds, Double tourDistanceKilometers, String routeInformation, List<TourLogReqModel> tourLogs)  {
        this.id = id;
        this.name = name;
        this.description = description;
        this.vehicle = vehicle;
        this.start = start;
        this.end = end;
        this.estimatedTimeSeconds = estimatedTimeSeconds;
        this.tourDistanceKilometers = tourDistanceKilometers;
        this.routeInformation = routeInformation;
        this.tourLogs = tourLogs;
    }
    //endregion

    //region getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public List<TourLogReqModel> getTourLogs() {
        return tourLogs;
    }

    public void setTourLogs(List<TourLogReqModel> tourLogs) {
        this.tourLogs = tourLogs;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Long getEstimatedTimeSeconds() {
        return estimatedTimeSeconds;
    }

    public void setEstimatedTimeSeconds(Long estimatedTimeSeconds) {
        this.estimatedTimeSeconds = estimatedTimeSeconds;
    }

    public Double getTourDistanceKilometers() {
        return tourDistanceKilometers;
    }

    public void setTourDistanceKilometers(Double tourDistanceKilometers) {
        this.tourDistanceKilometers = tourDistanceKilometers;
    }

    public void setRouteInformation(String newRouteInformation) {
        this.routeInformation = newRouteInformation;
    }

    public String getRouteInformation() {
        return this.routeInformation;
    }
    //end region
}
