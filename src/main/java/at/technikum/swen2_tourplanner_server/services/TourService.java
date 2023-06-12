package at.technikum.swen2_tourplanner_server.services;

import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.exceptions.TourCreationErrorExc;
import at.technikum.swen2_tourplanner_server.repositories.TourRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourService {
    protected final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
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

    @Transactional
    public Long createTour(Tour newTour) {

        if (newTour.getStart().getCoordinate().equals(newTour.getEnd().getCoordinate())) {
            throw new TourCreationErrorExc("Tour start and tour end have to be different");
        }

        return this.tourRepository.save(newTour).getId();
    }
}
