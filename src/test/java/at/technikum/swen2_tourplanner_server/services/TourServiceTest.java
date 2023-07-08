package at.technikum.swen2_tourplanner_server.services;

import at.technikum.swen2_tourplanner_server.dto.TourDto;
import at.technikum.swen2_tourplanner_server.entities.enums.Vehicle;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.RecordNotFoundExc;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourRepository;
import at.technikum.swen2_tourplanner_server.restServer.services.TourService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase
public class TourServiceTest {

    private final TourService tourService;

    private final TourDto invalidNameTour = new TourDto(
            "Invalid @ Name",
            "Description",
            Vehicle.BIKE,
            "start",
            "end",
            200L,
            10.5,
            "route info"
    );

    private final TourDto invalidDescriptionTour = new TourDto(
            "Valid Name",
            "@ Invalid Description",
            Vehicle.BIKE,
            "start",
            "end",
            200L,
            10.5,
            "route info"
    );

    private final TourDto validTour = new TourDto(
            "Valid Name",
            "Valid Description",
            Vehicle.BIKE,
            "start",
            "end",
            200L,
            10.5,
            "route info"
    );


    public TourServiceTest(@Autowired TourRepository tourRepository) {
        this.tourService = new TourService(tourRepository);
    }


    @Test
    public void createInvalidName() {

        assertThrows(RuntimeException.class, () -> this.tourService.createTour(this.invalidNameTour));

    }


    @Test
    public void createInvalidDesc() {

        assertThrows(RuntimeException.class, () -> this.tourService.createTour(this.invalidDescriptionTour));

    }

    @Test
    public void getById() {

        Long tourId = this.tourService.createTour(this.validTour).getTour().getId();


        assertDoesNotThrow(() -> this.tourService.getById(tourId));
        assertEquals(this.tourService.getById(tourId).getTour().getName(), this.validTour.getName());

        this.tourService.deleteTour(tourId);
    }

    @Test
    public void delete() {

        Long tourId = this.tourService.createTour(this.validTour).getTour().getId();


        assertDoesNotThrow(() -> this.tourService.deleteTour(tourId));
        assertThrows(RecordNotFoundExc.class, () -> this.tourService.deleteTour(tourId));

    }

}
