package at.technikum.swen2_tourplanner_server.entities;

import at.technikum.swen2_tourplanner_server.entities.enums.Vehicle;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

//todo add a version number to check the updates

@JsonIgnoreProperties({"hibernateLazyInitializer"})
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
    @Size(min = 10, max = 500, message = "A valid description must contain more than 10 characters and less than 500")
    private String description;

    @Column(name = "from_location", nullable = false, length = 50)
    @NotNull(message = "Start cannot be null")
    @NotBlank(message = "Start cannot be empty")
    @Size(min = 1, max = 50, message = "A valid name must contain more than 5 characters and less than 50")
    private String from;

    @Column(name = "to_location", nullable = false, length = 50)
    @NotNull(message = "To cannot be null")
    @NotBlank(message = "To cannot be empty")
    @Size(min = 1, max = 50, message = "A valid name must contain more than 5 characters and less than 50")
    private String to;

    @Column(name = "vehicle", nullable = false)
    @NotNull(message = "Vehicle cannot be null")
    @Enumerated(EnumType.ORDINAL)
    private Vehicle vehicle;

    @Column(name = "estimated_time_minutes", nullable = false)
    @NotNull(message = "Estimated time cannot be null")
    @Min(value = 1, message = "Estimated time in minutes has to be at least 1")
    private Long estimatedTimeSeconds;

    @Column(name = "distance_km", nullable = false)
    @NotNull(message = "Distance cannot be null")
    @Min(value = 0, message = "Tour distance in meters has to be at least 1")
    private Double tourDistanceKilometers;

    @Column(name = "route_information", nullable = false, length = 255)
    @NotNull(message = "The route information cannot be null")
    @NotBlank(message = "The route information cannot be empty")
    @Size(min = 1, max = 255, message = "A valid route information must contain more than 1 character and less than 255")
    private String routeInformation;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "tour")
    private List<TourLog> logs;

    //@Column(name = "popularity")
    //private Integer popularity;

    //@Column(name = "child_friendliness")
    //private Double childFriendliness;
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

    /*public Integer getPopularity() {
        return popularity;
    }
    public Double getChildFriendliness() {
        return childFriendliness;
    }*/
    //endregion

    //region setters
    public void setId(Long id) {
        this.id = id;
    }

    /*public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public void setChildFriendliness(Double childFriendliness) {
        this.childFriendliness = childFriendliness;
    }*/

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
    //endregion

    public Float getAvgTime() {

        Float avgTime = 0f;

        if (this.logs.size() != 0) {

            for (TourLog log : this.logs) {

                avgTime += log.getTotalTimeMinutes();

            }

            avgTime = avgTime/this.logs.size();
        }

        return avgTime;

    }

    public Float getAvgRating() {

        Float avgRating = 0f;

        if (this.logs.size() != 0) {

            for (TourLog log : this.logs) {

                avgRating += log.getRating().ordinal();

            }

            avgRating = avgRating/this.logs.size();

        }

        return avgRating;

    }

    public Float getAvgDifficulty() {

        Float avgDifficulty = 0f;

        if (this.logs.size() != 0) {

            for (TourLog log : this.logs) {

                avgDifficulty += log.getDifficulty().ordinal();

            }

            avgDifficulty = avgDifficulty/this.logs.size();

        }

        return avgDifficulty;

    }

}
