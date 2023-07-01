package at.technikum.swen2_tourplanner_server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TourStatsDto {

    private Integer popularity;

    private Double childFriendliness;

    private Double avgTime;
    private Double avgRating;
    private Double avgDifficulty;

    public TourStatsDto(Integer popularity, Double childFriendliness, Double avgTime, Double avgRating, Double avgDifficulty) {
        this.popularity = popularity;
        this.childFriendliness = childFriendliness;
        this.avgTime = avgTime;
        this.avgRating = avgRating;
        this.avgDifficulty = avgDifficulty;
    }
}
