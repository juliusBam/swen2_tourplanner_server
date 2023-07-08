package at.technikum.swen2_tourplanner_server.repositories;

import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.entities.enums.Vehicle;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourRepository;
import at.technikum.swen2_tourplanner_server.restServer.services.TourService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase
public class TourRepositoryTest {

    @Autowired
    private TourRepository tourRepository;

    private TourService tourService;

    private Tour tour1;

    private Tour tour2;

    public TourRepositoryTest() {
        this.tourService = new TourService(this.tourRepository);
    }

    private Tour invalidTourName = new Tour(
            "tour name @",
            "description",
            Vehicle.BIKE,
            "from",
            "to",
            30L,
            10.2D,
            "route info",
            null
    );

    @BeforeEach
    void setUp() {

        //clean db before each test
        this.tourRepository.deleteAll();

        this.tour1 = new Tour("first tour", "first tour description", Vehicle.BIKE,
                            "wien",
                            "bozen",
                            12L,
                            8.89,
                            "dasd",
                            null
                );
        this.tourRepository.save(tour1).getId();

        this.tour2 = new Tour("second tour", "second tour description", Vehicle.CAR,
                "lezzeno",
                "balbla",
                12L,
                8.99,
                "dasdsa",
                null
        );
        this.tourRepository.save(tour2).getId();


    }

    @Test
    void getById() {
        Optional<Tour> searchedTour = this.tourRepository.findById(this.tour1.getId());

        assertNotNull(searchedTour);
        assertTrue(searchedTour.isPresent());
        assertEquals(this.tour1.getName(), searchedTour.get().getName());
    }

    @Test
    void delete() {

        Long id = this.tour1.getId();

        this.tourRepository.deleteById(id);

        assertTrue(this.tourRepository.findById(id).isEmpty());

    }

    @Test
    void update() {

        List<Tour> recs = this.tourRepository.findAll();

        String newTourName = "New cool tour name";

        Tour newTour = new Tour(newTourName, "first tour description", Vehicle.BIKE,
                "wien",
                "bozen",
                12L,
                8.89,
                "dasdas",
                null
        );

        newTour.setId(this.tour1.getId());

        this.tourRepository.save(newTour);

        Optional<Tour> storedTour = this.tourRepository.findById(this.tour1.getId());

        assertNotNull(storedTour);
        assertTrue(storedTour.isPresent());
        assertEquals(newTourName, storedTour.get().getName());

    }

    @Test
    void create() {

        Tour newTour = new Tour("first tour", "first tour description", Vehicle.BIKE,
                "venedig",
                "paris",
                12L,
                8.89,
                "dasd",
                null
        );

        Long newTourId = this.tourRepository.save(newTour).getId();

        Optional<Tour> storedTour = this.tourRepository.findById(newTourId);

        assertTrue(storedTour.isPresent());
        assertEquals(storedTour.get().getName(), newTour.getName());
        assertEquals(storedTour.get().getDescription(), newTour.getDescription());
    }

}
