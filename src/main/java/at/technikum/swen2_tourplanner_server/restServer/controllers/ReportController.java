package at.technikum.swen2_tourplanner_server.restServer.controllers;

import at.technikum.swen2_tourplanner_server.dto.responses.TourReport;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourLogRepository;
import at.technikum.swen2_tourplanner_server.restServer.repositories.TourRepository;
import at.technikum.swen2_tourplanner_server.restServer.services.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/report")
public class ReportController {
    private final ReportService reportService;

    ReportController(TourRepository tourRepository, TourLogRepository tourLogRepository) {this.reportService = new ReportService(tourRepository, tourLogRepository);}

    @GetMapping(value = "/{id}")
    TourReport getReport(@PathVariable Long id) {
        return this.reportService.generateTourReport(id);
    }
}
