package at.technikum.swen2_tourplanner_server.BL;

import at.technikum.swen2_tourplanner_server.BL.model.AverageStatsModel;
import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.entities.TourLog;

public class StatsCalculator {
    public AverageStatsModel calculateAverageStats(Tour tour) {

        Double childFriendliness = 0D;
        Double avgTime = 0D;
        Double avgRating = 0D;
        Double avgDifficulty = 0D;

        if (tour.getLogs() != null && tour.getLogs().size() != 0) {

            for (TourLog tourLog : tour.getLogs()) {
                avgTime += tourLog.getTotalTimeMinutes();
                avgDifficulty += tourLog.getDifficulty().ordinal();
                avgRating += tourLog.getRating().ordinal();
            }

            //todo decide how to calculate the child friendliness
            childFriendliness += avgDifficulty;
            avgTime = avgTime/tour.getLogs().size();
            avgRating = avgRating/tour.getLogs().size();
            avgDifficulty = avgDifficulty/tour.getLogs().size();

        }

        return new AverageStatsModel(childFriendliness, avgTime, avgRating, avgDifficulty);
    }

}
