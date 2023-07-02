package at.technikum.swen2_tourplanner_server.restServer.services;

import at.technikum.swen2_tourplanner_server.BL.ReportGenerator;
import at.technikum.swen2_tourplanner_server.BL.ReportGeneratorOutput;
import at.technikum.swen2_tourplanner_server.Logging;
import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.ReportGenerationException;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourRepository;
import at.technikum.swen2_tourplanner_server.restServer.services.interfaces.IReportService;
import at.technikum.swen2_tourplanner_server.restServer.services.interfaces.ITourService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService extends Logging implements IReportService {
    private final ITourService tourService;

    private final ReportGenerator reportGenerator;

    public ReportService(TourRepository tourRepository) {
        this.tourService = new TourService(tourRepository);
        this.reportGenerator = new ReportGenerator();
    }

    @Override
    public ReportGeneratorOutput generateTourReport(Long tourId, String sessionId) {

        Tour requestedTour = this.tourService.getByIdEntityModel(tourId).orElseThrow(
                () -> {

                    Logging.logger.error("Error generating report for tour with id [{}], tour could not be found in the database", tourId);
                    return new ReportGenerationException("Tour cannot be found");
                }
        );

        return this.reportGenerator.generateTourReport(requestedTour, sessionId);
    }

    @Override
    public ReportGeneratorOutput generateSummarizeReport() {

        List<Tour> requestedTours = this.tourService.getAllEntityModel();

        if (requestedTours.size() == 0) {
            Logging.logger.error("Error generating summary report, but no reports could be found in the database");
            throw new ReportGenerationException("There are no reports in the database");
        }

        return this.reportGenerator.generateSummarizeReport(requestedTours);
    }
}
