package at.technikum.swen2_tourplanner_server;

import at.technikum.swen2_tourplanner_server.BL.StatsCalculator;
import at.technikum.swen2_tourplanner_server.BL.model.AverageStatsModel;
import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.entities.TourLog;
import at.technikum.swen2_tourplanner_server.entities.enums.Vehicle;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatsCalculatorTest {

    private final StatsCalculator statsCalculator = new StatsCalculator();

    private final Tour tour;

    public StatsCalculatorTest() {
        this.tour = new Tour();

        tour.setTourDistanceKilometers(30D);
        tour.setVehicle(Vehicle.BIKE);

        TourLog tourLog1 = new TourLog(
                1L,
                "Comment",
                5,
                40L,
                5,
                tour
        );

        TourLog tourLog2 = new TourLog(
                1L,
                "Comment",
                10,
                20L,
                0,
                tour
        );

        TourLog tourLog3 = new TourLog(
                1L,
                "Comment",
                10,
                80L,
                0,
                tour
        );

        TourLog tourLog4 = new TourLog(
                1L,
                "Comment",
                5,
                70L,
                5,
                tour
        );

        tour.setLogs(new ArrayList<>(List.of(tourLog1, tourLog2, tourLog3, tourLog4)));

    }

    @Test
    public void testTimeMultiplicator() {

        //act
        AverageStatsModel stats = this.statsCalculator.calculateAverageStats(tour);

        //assert
        assertEquals(this.statsCalculator.defineTimeMultiplicator(stats.avgTime()), 1.2D);
    }

    @Test
    public void testDistanceMultiplicator() {
        //act
        AverageStatsModel stats = this.statsCalculator.calculateAverageStats(tour);

        //assert
        assertEquals(this.statsCalculator.defineDistanceMultiplicator(this.tour.getVehicle(), tour.getTourDistanceKilometers()), 0.8D);
    }

    @Test
    public void testAvgStats() {

        //arrange

        //act
        AverageStatsModel stats = this.statsCalculator.calculateAverageStats(tour);

        //assert
        assertEquals(stats.avgRating(), 2.5D);
        assertEquals(stats.avgDifficulty(), 7.5D);
        assertEquals(stats.avgTime(), 52.5D);
        assertEquals(stats.childFriendliness(), 7.2D);

    }


}
