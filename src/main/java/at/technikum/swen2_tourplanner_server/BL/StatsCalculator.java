package at.technikum.swen2_tourplanner_server.BL;

import at.technikum.swen2_tourplanner_server.BL.model.AverageStatsModel;
import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.entities.TourLog;
import at.technikum.swen2_tourplanner_server.entities.enums.Vehicle;

public class StatsCalculator {
    public AverageStatsModel calculateAverageStats(Tour tour) {

        Double childFriendliness = 0D;
        Double avgTime = 0D;
        Double avgRating = 0D;
        Double avgDifficulty = 0D;

        if (tour.getLogs() != null && tour.getLogs().size() != 0) {

            for (TourLog tourLog : tour.getLogs()) {
                avgTime += tourLog.getTotalTimeMinutes();
                avgDifficulty += tourLog.getDifficulty();
                avgRating += tourLog.getRating();
            }

            avgTime = avgTime/tour.getLogs().size();
            avgRating = avgRating/tour.getLogs().size();
            avgDifficulty = avgDifficulty/tour.getLogs().size();
            childFriendliness += this.calculateChildFriendliness(avgDifficulty, avgTime, tour.getTourDistanceKilometers(), tour.getVehicle());

        }

        return new AverageStatsModel(childFriendliness, avgTime, avgRating, avgDifficulty);
    }

    public Double calculateChildFriendliness(Double avgDifficulty, Double avgTime, Double distance, Vehicle vehicle) {

        Double tmpChildFriendliness = avgDifficulty * this.defineTimeMultiplicator(avgTime) * this.defineDistanceMultiplicator(vehicle, distance);

        if (tmpChildFriendliness > 10) {
            return 10D;
        }

        if (tmpChildFriendliness < 0) {

            return 0D;

        }

        return tmpChildFriendliness;
    }

    public Double defineTimeMultiplicator(Double avgTime) {

        if (avgTime < 20)
            return 1.5D;

        if (avgTime < 80)
            return 1.2D;

        if (avgTime > 240)
            return 0.1D;

        if (avgTime > 180)
            return 0.5D;

        if (avgTime > 120)
            return 0.8D;

        return 1D;

    }

    public Double defineDistanceMultiplicator(Vehicle vehicle, Double distance) {

        if (vehicle.equals(Vehicle.CAR)) {

            if (distance < 50)
                return 1.5D;

            if (distance < 100)
                return 1.2D;

            if (distance > 360)
                return 0.1D;

            if (distance > 260)
                return 0.5D;

            if (distance > 200)
                return 0.8D;

            return 1D;

        } else {

            if (distance < 5)
                return 1.5D;

            if (distance < 10)
                return 1.2D;

            if (distance > 50)
                return 0.1D;

            if (distance > 40)
                return 0.5D;

            if (distance > 20)
                return 0.8D;

            return 1D;

        }


    }

}
