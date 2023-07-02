package at.technikum.swen2_tourplanner_server.dto;

import at.technikum.swen2_tourplanner_server.entities.enums.Difficulty;
import at.technikum.swen2_tourplanner_server.entities.enums.Rating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//todo add param for force update --> see version

@Getter
@Setter
public class TourLogDto {
    public TourLogDto() {
    }

        private Long id;
    	private Long timeStamp;
        private String comment;

        private Integer difficulty;

        private Long totalTimeMinutes;

        private Integer rating;

        private Long tourId;

    public TourLogDto(Long timeStamp, String comment, Integer difficulty, Long totalTimeMinutes, Integer rating, Long tourId) {
        this.timeStamp = timeStamp;
        this.comment = comment;
        this.difficulty = difficulty;
        this.totalTimeMinutes = totalTimeMinutes;
        this.rating = rating;
        this.tourId = tourId;
    }

    public TourLogDto(Long tourLogId, Long timeStamp, String comment, Integer difficulty, Long totalTimeMinutes, Integer rating, Long tourId) {
        this.id = tourLogId;
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

    public Integer getDifficulty() {
        return difficulty;
    }

    public Long getTotalTimeMinutes() {
        return totalTimeMinutes;
    }

    public Integer getRating() {
        return rating;
    }

    public Long getTourId() {
        return tourId;
    }

    public Long getId() {return id;}
    //endregion
}
