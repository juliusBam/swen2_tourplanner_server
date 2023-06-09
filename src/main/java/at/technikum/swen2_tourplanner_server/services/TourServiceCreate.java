package at.technikum.swen2_tourplanner_server.services;

import at.technikum.swen2_tourplanner_server.entities.Stop;
import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.repositories.TourRepository;
import jakarta.transaction.Transactional;
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
        Long tourId = this.tourRepository.save(newTour).getId();

        //https://www.stackchief.com/blog/One%20To%20Many%20Example%20%7C%20Spring%20Data%20JPA
        //this.stopService.createStop(newTour.getStart());

        //this.stopService.createStop(newTour.getEnd());

        return tourId;
    }
}
