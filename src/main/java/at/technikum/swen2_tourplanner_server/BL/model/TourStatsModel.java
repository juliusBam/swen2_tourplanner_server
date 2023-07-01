package at.technikum.swen2_tourplanner_server.BL.model;

public record TourStatsModel(Integer popularity, Double childFriendliness,
                             Double avgTime, Double avgRating, Double avgDifficulty) {

}
