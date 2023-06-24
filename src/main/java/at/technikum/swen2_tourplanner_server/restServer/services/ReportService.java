package at.technikum.swen2_tourplanner_server.restServer.services;

import at.technikum.swen2_tourplanner_server.BL.ReportGenerator;
import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.dto.responses.TourReport;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final TourService tourService;

    private final ReportGenerator reportGenerator;

    public ReportService(TourRepository tourRepository) {
        this.tourService = new TourService(tourRepository);
        this.reportGenerator = new ReportGenerator();
    }

    public byte[] generateTourReport(Long tourId) {

        Tour requestedTour = this.tourService.getById(tourId).orElseThrow();

        return this.reportGenerator.generateTourReport(requestedTour);
    }

    public TourReport generateSummarizeReport(Long tourId) {

        Tour requestedTour = this.tourService.getById(tourId).orElseThrow();

        return new TourReport(
                this.reportGenerator.generateSummarizeReport(requestedTour)
        );
    }
}
