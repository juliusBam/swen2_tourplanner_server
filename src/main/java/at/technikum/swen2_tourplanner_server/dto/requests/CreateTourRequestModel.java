package at.technikum.swen2_tourplanner_server.dto.requests;

import at.technikum.swen2_tourplanner_server.entities.Location;
import at.technikum.swen2_tourplanner_server.entities.enums.Vehicle;

public class CreateTourRequestModel {

    //region fields
    private Long id;
    private String name;

    private String description;

    private Location start;

    private Location end;

    private Vehicle vehicle;

    private Long estimatedTimeSeconds;

    private Long tourDistanceKilometers;
    //endregion

    //region constructors
    public CreateTourRequestModel() {}
    public CreateTourRequestModel(Long id, String name, String description, Vehicle vehicle, Location start, Location end, Long estimatedTimeSeconds, Long tourDistanceKilometers)  {
        this.id = id;
        this.name = name;
        this.description = description;
        this.vehicle = vehicle;
        this.start = start;
        this.end = end;
        this.estimatedTimeSeconds = estimatedTimeSeconds;
        this.tourDistanceKilometers = tourDistanceKilometers;
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

    public Location getStart() {
        return start;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public Location getEnd() {
        return end;
    }

    public void setEnd(Location end) {
        this.end = end;
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

    public Long getTourDistanceKilometers() {
        return tourDistanceKilometers;
    }

    public void setTourDistanceKilometers(Long tourDistanceKilometers) {
        this.tourDistanceKilometers = tourDistanceKilometers;
    }
    //end region
}
