package at.technikum.swen2_tourplanner_server.entities;

import at.technikum.swen2_tourplanner_server.entities.enums.Vehicle;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

@Entity(name = "tours")
public class Tour implements Serializable {

    //region fields
    @Id
    @GeneratedValue
    @Column(name = "tour_id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    @NotNull(message = "Tour name cannot be null")
    @NotBlank(message = "Tour name cannot be empty")
    @Size(min = 5, max = 50, message = "A valid name must contain more than 5 characters and less than 50")
    private String name;

    @Column(name = "description", nullable = true, length = 500)
    @Size(min = 10, max = 50, message = "A valid description must contain more than 10 characters and less than 500")
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    private Stop start;

    @OneToOne(cascade = CascadeType.ALL)
    private Stop end;

    @Column(name = "vehicle", nullable = false)
    @NotNull(message = "Vehicle cannot be null")
    @Enumerated(EnumType.ORDINAL)
    private Vehicle vehicle;

    @Column(name = "estimated_time_minutes", nullable = false)
    @NotNull(message = "Estimated time cannot be null")
    @Min(value = 1, message = "Estimated time in minutes has to be at least 1")
    private Long estimatedTimeMinutes;

    @Column(name = "distance_meters", nullable = false)
    @NotNull(message = "Distance cannot be null")
    @Min(value = 1, message = "Tour distance in meters has to be at least 1")
    private Long tourDistanceMeters;

    @Column(name = "tour_image", nullable = false)
    @NotNull(message = "The tour image has to be set")
    @NotBlank(message = "The tour image content cannot be empty")
    private String base64Image;
    //endregion

    //region constructors
    public Tour() {

    }

    Tour(String name, String description, Vehicle vehicle, Stop start, Stop end, Long estimatedTimeMinutes, Long tourDistanceMeters, String tourImage) {
        this.name = name;
        this.description = description;
        this.vehicle = vehicle;
        this.start = start;
        this.end = end;
        this.estimatedTimeMinutes = estimatedTimeMinutes;
        this.tourDistanceMeters = tourDistanceMeters;
        this.base64Image = tourImage;
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

    public String getBase64Image() {
        return base64Image;
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
    //endregion

    //region setters
    public void setStart(Stop start) {
        this.start = start;
    }

    public void setEnd(Stop end) {
        this.end = end;
    }
    //endregion

}
