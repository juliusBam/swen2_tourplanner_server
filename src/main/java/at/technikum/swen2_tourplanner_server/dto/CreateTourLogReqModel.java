package at.technikum.swen2_tourplanner_server.dto;

import at.technikum.swen2_tourplanner_server.entities.enums.Difficulty;
import at.technikum.swen2_tourplanner_server.entities.enums.Rating;

public class CreateTourLogReqModel {

        private Long id;
    	private Long timeStamp;
        private String comment;

        private Difficulty difficulty;

        private Long totalTimeMinutes;

        private Rating rating;

        private Long tourId;

    public CreateTourLogReqModel(Long timeStamp, String comment, Difficulty difficulty, Long totalTimeMinutes, Rating rating, Long tourId) {
        this.timeStamp = timeStamp;
        this.comment = comment;
        this.difficulty = difficulty;
        this.totalTimeMinutes = totalTimeMinutes;
        this.rating = rating;
        this.tourId = tourId;
    }

    //region getters
    public Long getTimeStamp() {
        return timeStamp;
    }

    public String getComment() {
        return comment;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Long getTotalTimeMinutes() {
        return totalTimeMinutes;
    }

    public Rating getRating() {
        return rating;
    }

    public Long getTourId() {
        return tourId;
    }

    public Long getId() {return id;}
    //endregion
}
