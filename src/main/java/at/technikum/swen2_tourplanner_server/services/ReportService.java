package at.technikum.swen2_tourplanner_server.services;

import at.technikum.swen2_tourplanner_server.models.TourReport;
import at.technikum.swen2_tourplanner_server.repositories.TourRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final TourServiceBase tourService;

    public ReportService(TourRepository tourRepository) {
        this.tourService = new TourServiceBase(tourRepository);
    }

    public TourReport generateTourReport(Long id) {
        return new TourReport("I am a tour report");
    }

    public TourReport generateSummarizeReport(Long id) {
        return new TourReport("I am a summarize report");
    }
}
