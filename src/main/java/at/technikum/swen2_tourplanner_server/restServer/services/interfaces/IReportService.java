package at.technikum.swen2_tourplanner_server.restServer.services.interfaces;

import at.technikum.swen2_tourplanner_server.BL.ReportGeneratorOutput;

public interface IReportService {
    ReportGeneratorOutput generateTourReport(Long tourId, String sessionId);

    ReportGeneratorOutput generateSummarizeReport();
}
