package at.technikum.swen2_tourplanner_server.restServer.services;

import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.helpers.reports.ReportGenerator;
import at.technikum.swen2_tourplanner_server.dto.responses.TourReport;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourLogRepository;
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

    public TourReport generateTourReport(Long id) {

        Tour requestedTour = this.tourService.getById(id).orElseThrow();

        return new TourReport(
                this.reportGenerator.generateTourReport(requestedTour)
        );
    }

    public TourReport generateSummarizeReport(Long id) {

        Tour requestedTour = this.tourService.getById(id).orElseThrow();

        return new TourReport(
                this.reportGenerator.generateSummarizeReport(requestedTour)
        );
    }
}
