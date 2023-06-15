package at.technikum.swen2_tourplanner_server.repositories;

import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.entities.TourLog;
import at.technikum.swen2_tourplanner_server.entities.enums.Difficulty;
import at.technikum.swen2_tourplanner_server.entities.enums.Rating;
import at.technikum.swen2_tourplanner_server.entities.enums.Vehicle;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourLogRepository;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.*;

@DataJpaTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TourLogRepositoryTest {

    @Autowired
    private TourLogRepository tourLogRepository;

    @Autowired
    private TourRepository tourRepository;

    private Tour tour1;

    private Tour tour2;

    private TourLog tourLog1;

    private TourLog tourLog2;

    //todo add a delete ad setUp
    @BeforeEach
    void setUp() {

        //create tour 1
        this.tour1 = new Tour("first tour", "first tour description", Vehicle.BIKE,
                "wien",
                "bozen",
                12L,
                8.23,
                "".getBytes(),
                null
        );
        this.tour1.setId(1L);
        this.tourRepository.save(tour1);

        //add tour log 1 and 2 to tour 1
        this.tourLog1 = new TourLog(11123L, "tourlog 1 comment", Difficulty.EASY, 123L, Rating.GOOD, tour1);
        this.tourLog1.setId(1L);

        this.tourLogRepository.save(tourLog1);

        this.tourLog2 = new TourLog(11123L, "tourlog 2 comment", Difficulty.HARD, 123L, Rating.DECENT, tour1);
        this.tourLog2.setId(2L);

        this.tourLogRepository.save(tourLog2);

        //create tour2 w/o tourlogs
        this.tour2 = new Tour("second tour", "second tour description", Vehicle.BIKE,
                "wien",
                "bozen",
                12L,
                8.00,
                "".getBytes(),
                null
        );
        this.tour2.setId(2L);

        this.tourRepository.save(tour2);

    }

    @Test
    void fetchAllByTour() {

        List<TourLog> tourLogs = this.tourLogRepository.findTourLogsByTourId(1L);

        assertTrue(tourLogs.size() != 0);
        assertTrue(tourLogs.stream().anyMatch(tourLog -> Objects.equals(tourLog.getId(), this.tourLog1.getId())));
        assertTrue(tourLogs.stream().anyMatch(tourLog -> Objects.equals(tourLog.getId(), this.tourLog2.getId())));
    }

    @Test
    void fetchById() {

        Optional<TourLog> tourLog = this.tourLogRepository.findById(1L);

        assertNotNull(tourLog);
        assertTrue(tourLog.isPresent());
        assertEquals(tourLog1.getComment(),tourLog.get().getComment());

    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void delete() {

        this.tourLogRepository.deleteById(1L);

        Optional<TourLog> tourLog = this.tourLogRepository.findById(1L);

        assertNotNull(tourLog);
        assertNotNull(tourLog.isEmpty());

    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void update() {

        String newTourComment = "tourlog 1 modified comment";

        TourLog newTourLog = new TourLog(11123L, newTourComment, Difficulty.EASY, 123L, Rating.GOOD, tour1);
        newTourLog.setId(this.tour2.getId());

        this.tourLogRepository.save(newTourLog);

        Optional<TourLog> storedTourLog = this.tourLogRepository.findById(this.tour2.getId());

        assertTrue(storedTourLog.isPresent());
        assertEquals(newTourComment, storedTourLog.get().getComment());

    }



}
