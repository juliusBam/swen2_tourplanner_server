package at.technikum.swen2_tourplanner_server.restServer.controllers;

import at.technikum.swen2_tourplanner_server.BL.model.ReportGeneratorOutput;
import at.technikum.swen2_tourplanner_server.MapQuestSource;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourRepository;
import at.technikum.swen2_tourplanner_server.restServer.services.ReportService;
import at.technikum.swen2_tourplanner_server.restServer.services.interfaces.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/report")
public class ReportController {
    private final IReportService reportService;

    ReportController(TourRepository tourRepository, @Autowired MapQuestSource mapQuestSource) {
        this.reportService = new ReportService(tourRepository, mapQuestSource);
    }

    @GetMapping(value = "/{id}")
    HttpEntity<byte[]> getReport(@PathVariable Long id, @RequestParam String sessionId) {

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);

        ReportGeneratorOutput createdReport = this.reportService.generateTourReport(id, sessionId);

        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + createdReport.reportName());

        return new HttpEntity<byte[]>(createdReport.reportContent(), header);
    }

    @GetMapping(value = "/summarize")
    HttpEntity<byte[]> getSummarizeReport() {

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);

        ReportGeneratorOutput createdReport = this.reportService.generateSummarizeReport();

        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=summarized_" + createdReport.reportName());

        return new HttpEntity<byte[]>(createdReport.reportContent(), header);
    }

}
