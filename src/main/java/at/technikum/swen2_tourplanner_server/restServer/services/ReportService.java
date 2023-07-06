package at.technikum.swen2_tourplanner_server.restServer.services;

import at.technikum.swen2_tourplanner_server.BL.ReportGenerator;
import at.technikum.swen2_tourplanner_server.BL.model.ReportGeneratorOutput;
import at.technikum.swen2_tourplanner_server.BL.model.ReportInputData;
import at.technikum.swen2_tourplanner_server.BL.model.TourStatsModel;
import at.technikum.swen2_tourplanner_server.Logging;
import at.technikum.swen2_tourplanner_server.MapQuestSource;
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

    public ReportService(TourRepository tourRepository, MapQuestSource mapQuestSource) {
        this.tourService = new TourService(tourRepository);
        this.reportGenerator = new ReportGenerator(mapQuestSource);
    }

    @Override
    public ReportGeneratorOutput generateTourReport(Long tourId, String sessionId) {

        Tour requestedTour = this.tourService.getByIdEntityModel(tourId).orElseThrow(
                () -> {

                    Logging.logger.error(String.format("Error generating report for tour with id [{%d}], tour could not be found in the database", tourId));
                    return new ReportGenerationException("Tour cannot be found");
                }
        );

        TourStatsModel tourStatsModel = this.tourService.calculateTourStats(requestedTour);

        return this.reportGenerator.generateTourReport(new ReportInputData(requestedTour, tourStatsModel), sessionId);
    }

    @Override
    public ReportGeneratorOutput generateSummarizeReport() {

        List<ReportInputData> requestedTours = this.tourService.getAllSummarizeReport();

        if (requestedTours.size() == 0) {
            Logging.logger.info("Generating summary report, but no tours could be found in the database");
            throw new ReportGenerationException("There are no tours in the database");
        }

        return this.reportGenerator.generateSummarizeReport(requestedTours);
    }
}
