package at.technikum.swen2_tourplanner_server.services;

import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.repositories.TourRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourServiceBase {
    protected final TourRepository tourRepository;

    public TourServiceBase(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public Long updateTour(Tour updatedTour) {
        return this.tourRepository.save(updatedTour).getId();
    }

    public void deleteTour(Long id) {
        this.tourRepository.deleteById(id);
    }

    public void importTour(Tour newTour) {
        //TODO parse the json into a new tour
        this.tourRepository.save(newTour);
    }

    public String exportTour(Long id) {
        Tour tour = this.tourRepository.getById(id);
        //TODO parse tour into json
        return tour.toString();
    }

    public List<Tour> getAll() {
        return this.tourRepository.findAll();
    }

    public Optional<Tour> getById(Long id) {
        return this.tourRepository.findById(id);
    }

}
