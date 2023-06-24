package at.technikum.swen2_tourplanner_server.restServer.controllers;

import at.technikum.swen2_tourplanner_server.dto.responses.TourReport;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourRepository;
import at.technikum.swen2_tourplanner_server.restServer.services.ReportService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/report")
public class ReportController {
    private final ReportService reportService;

    ReportController(TourRepository tourRepository) {this.reportService = new ReportService(tourRepository);}

    @GetMapping(value = "/{id}")
    HttpEntity<byte[]> getReport(@PathVariable Long id) {

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "blabla.pdf");

        return new HttpEntity<byte[]>(this.reportService.generateTourReport(id), header);
    }

    @GetMapping(value = "/summarize/{id}")
    TourReport getSummarizeReport(@PathVariable Long id) {
        return this.reportService.generateSummarizeReport(id);
    }

}
