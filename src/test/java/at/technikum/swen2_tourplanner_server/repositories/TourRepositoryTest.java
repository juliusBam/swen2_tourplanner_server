package at.technikum.swen2_tourplanner_server.repositories;

import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.entities.enums.Vehicle;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.ANY)
public class TourRepositoryTest {

    @Autowired
    private TourRepository tourRepository;

    private Tour tour1;

    private Tour tour2;
    @BeforeEach
    void setUp() {

        this.tour1 = new Tour("first tour", "first tour description", Vehicle.BIKE,
                            "wien",
                            "bozen",
                            12L,
                            8.89,
                            "".getBytes(),
                            null
                );
        this.tour1.setId(1L);
        this.tourRepository.save(tour1);

        this.tour2 = new Tour("second tour", "second tour description", Vehicle.CAR,
                "lezzeno",
                "balbla",
                12L,
                8.99,
                "".getBytes(),
                null
        );
        this.tour2.setId(2L);
        this.tourRepository.save(tour2);


    }

    @Test
    void getById() {
        Optional<Tour> searchedTour = this.tourRepository.findById(2L);

        assertNotNull(searchedTour);
        assertTrue(searchedTour.isPresent());
        assertEquals(this.tour2.getName(), searchedTour.get().getName());
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void delete() {

        Long id = this.tour1.getId();

        this.tourRepository.deleteById(id);

        assertTrue(this.tourRepository.findById(id).isEmpty());

    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void update() {

    }

}
