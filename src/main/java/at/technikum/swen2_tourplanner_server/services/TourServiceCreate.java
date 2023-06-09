package at.technikum.swen2_tourplanner_server.services;

import at.technikum.swen2_tourplanner_server.entities.Stop;
import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.repositories.TourRepository;

public class TourServiceCreate extends TourServiceBase {

    private final StopService stopService;

    public TourServiceCreate(TourRepository tourRepository, StopService stopService) {
        super(tourRepository);
        this.stopService = stopService;
    }

    public Long createTour(Tour newTour) {

        //https://www.stackchief.com/blog/One%20To%20Many%20Example%20%7C%20Spring%20Data%20JPA


        Stop start = this.stopService.createStop(newTour.getStart());
        newTour.setStart(start);

        Stop end = this.stopService.createStop(newTour.getEnd());
        newTour.setEnd(end);

        return this.tourRepository.save(newTour).getId();
    }
}
