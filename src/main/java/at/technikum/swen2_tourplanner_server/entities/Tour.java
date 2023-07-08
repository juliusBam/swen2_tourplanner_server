package at.technikum.swen2_tourplanner_server.entities;

import at.technikum.swen2_tourplanner_server.entities.enums.Vehicle;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity(name = "tours")
public class Tour implements Serializable {

    //region fields
    @Id
    @GeneratedValue
    @Column(name = "tour_id")
    private Long id;

    @Column(name = "name", nullable = false, unique = false, length = 50)
    @NotNull(message = "Tour name cannot be null")
    @NotBlank(message = "Tour name cannot be empty")
    @Size(min = 1, max = 50, message = "A valid name must contain more than 0 characters and less than 50")
    @Pattern(regexp = "^[^@]*$", message = "The name cannot contain the symbol @")
    private String name;

    @Column(name = "description", nullable = true, length = 500)
    @Size(min = 1, max = 500, message = "A valid description must contain more than 0 characters and less than 500")
    @Pattern(regexp = "^[^@]*$", message = "The description cannot contain the symbol @")
    private String description;

    @Column(name = "from_location", nullable = false, length = 50)
    @NotNull(message = "Start cannot be null")
    @NotBlank(message = "Start cannot be empty")
    @Size(min = 1, max = 50, message = "A valid start must contain more than 0 characters and less than 50")
    private String from;

    @Column(name = "to_location", nullable = false, length = 50)
    @NotNull(message = "To cannot be null")
    @NotBlank(message = "To cannot be empty")
    @Size(min = 1, max = 50, message = "A valid destination must contain more than 0 characters and less than 50")
    private String to;

    @Column(name = "vehicle", nullable = false)
    @NotNull(message = "Vehicle cannot be null")
    @Enumerated(EnumType.ORDINAL)
    private Vehicle vehicle;

    @Column(name = "estimated_time_minutes", nullable = false)
    @NotNull(message = "Estimated time cannot be null")
    @Min(value = 0, message = "Estimated time must be positive and non-zero")
    private Long estimatedTimeSeconds;

    @Column(name = "distance_km", nullable = false)
    @NotNull(message = "Distance cannot be null")
    @Min(value = 0, message = "Tour distance must be positive and non-zero")
    private Double tourDistanceKilometers;

    @Column(name = "route_information", nullable = false, length = 1000)
    @NotNull(message = "The route information cannot be null")
    @NotBlank(message = "The route information cannot be empty")
    @Size(min = 1, max = 1000, message = "A valid route information must contain more than 0 character and less than 1000")
    private String routeInformation;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "tour")
    private List<TourLog> logs;
    //endregion

    //region constructors
    public Tour() {

    }

    public Tour(String name, String description, Vehicle vehicle, String from, String to, Long estimatedTimeSeconds, Double tourDistanceKilometers, String routeInformation, List<TourLog> logs)  {
        this.name = name;
        this.description = description;
        this.vehicle = vehicle;
        this.from = from;
        this.to = to;
        this.estimatedTimeSeconds = estimatedTimeSeconds;
        this.tourDistanceKilometers = tourDistanceKilometers;
        this.routeInformation = routeInformation;
        this.logs = logs;
    }
    //endregion

    //region getters
    public Long getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getRouteInformation() {
        return routeInformation;
    }

    public Long getEstimatedTimeSeconds() {
        return estimatedTimeSeconds;
    }

    public Double getTourDistanceKilometers() {
        return tourDistanceKilometers;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public List<TourLog> getLogs() {
        return this.logs;
    }
    //endregion

    //region setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setLogs(List<TourLog> newLogs) {
        this.logs = newLogs;
    }

    public void addLog(TourLog newLog) {
        this.logs.add(newLog);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setTourDistanceKilometers(Double tourDistanceKilometers) {
        this.tourDistanceKilometers = tourDistanceKilometers;
    }
    //endregion

}
