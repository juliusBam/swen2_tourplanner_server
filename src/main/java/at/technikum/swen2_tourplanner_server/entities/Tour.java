package at.technikum.swen2_tourplanner_server.entities;

import at.technikum.swen2_tourplanner_server.entities.enums.Vehicle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity(name = "tours")
public class Tour {

    //region fields
    @Id
    @GeneratedValue
    @Column(name = "tour_id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    @NotNull(message = "Tour name cannot be null")
    @NotBlank(message = "Tour name cannot be empty")
    private String name;

    @Column(name = "description", nullable = true, length = 500)
    private String description;

    //@Column(name = "start_stop", nullable = false)
    //@NotNull(message = "Start cannot be null")
    //@NotBlank(message = "Reference to start cannot be empty")
    @ManyToOne(cascade = CascadeType.ALL)
    private Stop start;

    //@Column(name = "end_stop", nullable = false)
    //@NotNull(message = "End cannot be null")
    //@NotBlank(message = "Reference to end cannot be empty")
    @ManyToOne(cascade = CascadeType.ALL)
    private Stop end;

    @Column(name = "vehicle", nullable = false)
    @NotNull(message = "Vehicle cannot be null")
    @Enumerated(EnumType.ORDINAL)
    private Vehicle vehicle;

    @Column(name = "estimated_time_minutes", nullable = false)
    @NotNull(message = "Estimated time cannot be null")
    private Long estimatedTimeMinutes;

    @Column(name = "distance_meters", nullable = false)
    @NotNull(message = "Distance cannot be null")
    private Long tourDistanceMeters;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
    private List<TourLog> logs;
    //endregion

    //region constructors
    public Tour() {

    }

    Tour(String name, String description, Vehicle vehicle, Stop start, Stop end, Long estimatedTimeMinutes, Long tourDistanceMeters, List<TourLog> logs) {
        this.name = name;
        this.description = description;
        this.vehicle = vehicle;
        this.start = start;
        this.end = end;
        this.estimatedTimeMinutes = estimatedTimeMinutes;
        this.tourDistanceMeters = tourDistanceMeters;
        this.logs = logs;
    }
    //endregion

    //region getters
    public Long getId() {
        return id;
    }

    public Stop getStart() {
        return start;
    }

    public Stop getEnd() {
        return end;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Long getEstimatedTimeMinutes() {
        return estimatedTimeMinutes;
    }

    public Long getTourDistanceMeters() {
        return tourDistanceMeters;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public List<TourLog> getLogs() {
        return logs;
    }
    //endregion

    //region setters
    public void setLogs(List<TourLog> logs) {
        this.logs = logs;
    }

    public void setStart(Stop start) {
        this.start = start;
    }

    public void setEnd(Stop end) {
        this.end = end;
    }
    //endregion

}
