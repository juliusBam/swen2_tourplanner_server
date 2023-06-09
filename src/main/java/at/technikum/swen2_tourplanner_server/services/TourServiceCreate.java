package at.technikum.swen2_tourplanner_server.services;

import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.exceptions.TourCreationErrorExc;
import at.technikum.swen2_tourplanner_server.repositories.TourRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class TourServiceCreate extends TourServiceBase {

    private final StopService stopService;

    public TourServiceCreate(TourRepository tourRepository, StopService stopService) {
        super(tourRepository);
        this.stopService = stopService;
    }

    @Transactional
    public Long createTour(Tour newTour) {
        //validation
        if (newTour.getStart().getCoordinate().equals(newTour.getEnd().getCoordinate())) {
            throw new TourCreationErrorExc("Tour start and tour end have to be different");
        }

            return this.tourRepository.save(newTour).getId();
    }
}
