package at.technikum.swen2_tourplanner_server.entities;

import at.technikum.swen2_tourplanner_server.entities.enums.Difficulty;
import at.technikum.swen2_tourplanner_server.entities.enums.Rating;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity(name = "tour_log")
public class TourLog {
    @Id
    @GeneratedValue
    @Column(name = "tour_log_id")
    private Long id;

    @Column(name = "time_stamp", nullable = false)
    @NotNull(message = "Timestamp cannot be null")
    @NotBlank(message = "Timestamp cannot be empty")
    private Long timeStamp;

    @Column(name = "tour_log_comment", nullable = false, length = 500)
    @NotNull(message = "Tour log comment cannot be null")
    @NotBlank(message = "Tour log comment cannot be empty")
    private String comment;

    @Column(name = "tour_log_difficulty", nullable = false)
    @NotNull(message = "Tour log difficulty cannot be null")
    @NotBlank(message = "Tour log difficulty cannot be empty")
    private Difficulty difficulty;

    @Column(name = "tour_log_total_time_minutes", nullable = false)
    @NotNull(message = "Tour log total time cannot be null")
    @NotBlank(message = "Tour log total time cannot be empty")
    private Long totalTimeMinutes;

    @Column(name = "tour_log_rating", nullable = false)
    @NotNull(message = "Tour log rating cannot be null")
    @NotBlank
    private Rating rating;

    public TourLog() {}

    public TourLog(Long timeStamp, String comment, Difficulty difficulty, Long totalTimeMinutes, Rating rating) {
        this.timeStamp = timeStamp;
        this.comment = comment;
        this.difficulty = difficulty;
        this.totalTimeMinutes = totalTimeMinutes;
        this.rating = rating;
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
    //endregion
}
