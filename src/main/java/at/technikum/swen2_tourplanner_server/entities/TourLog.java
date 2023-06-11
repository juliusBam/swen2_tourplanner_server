package at.technikum.swen2_tourplanner_server.entities;

import at.technikum.swen2_tourplanner_server.entities.enums.Difficulty;
import at.technikum.swen2_tourplanner_server.entities.enums.Rating;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

@Entity(name = "tour_log")
public class TourLog implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "tour_log_id")
    private Long id;

    @Column(name = "time_stamp", nullable = false, precision = 10)
    @NotNull(message = "Timestamp cannot be null")
    private Long timeStamp;

    @Column(name = "tour_log_comment", nullable = false, length = 500)
    @NotNull(message = "Tour log comment cannot be null")
    @NotBlank(message = "Tour log comment cannot be empty")
    @Size(min = 5, max = 500, message = "A valid tour log comment must contain more than 5 characters and less than 500")
    private String comment;

    @Column(name = "tour_log_difficulty", nullable = false)
    @NotNull(message = "Tour log difficulty cannot be null")
    private Difficulty difficulty;

    @Column(name = "tour_log_total_time_minutes", nullable = false)
    @NotNull(message = "Tour log total time cannot be null")
    @Min(value = 1, message = "The total time in minutes cannot be 0")
    private Long totalTimeMinutes;

    @Column(name = "tour_log_rating", nullable = false)
    @NotNull(message = "Tour log rating cannot be null")
    private Rating rating;

    @ManyToOne
    @JoinColumn(name="tour_id", nullable=false)
    private Tour tour;

    public TourLog() {}

    public TourLog(Long timeStamp, String comment, Difficulty difficulty, Long totalTimeMinutes, Rating rating, Tour tour) {
        this.timeStamp = timeStamp;
        this.comment = comment;
        this.difficulty = difficulty;
        this.totalTimeMinutes = totalTimeMinutes;
        this.rating = rating;
        this.tour = tour;
    }

    //region getters
    public Long getId() {return this.id;}
    public Long getTimeStamp() {
        return this.timeStamp;
    }
    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Tour getTour() {
        return tour;
    }
    //endregion

    //region setters
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Long getTotalTimeMinutes() {
        return totalTimeMinutes;
    }

    public void setTotalTimeMinutes(Long totalTimeMinutes) {
        this.totalTimeMinutes = totalTimeMinutes;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }
    //endregion
}
