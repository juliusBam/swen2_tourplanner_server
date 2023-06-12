package at.technikum.swen2_tourplanner_server.controllers;

import at.technikum.swen2_tourplanner_server.dto.TourReport;
import at.technikum.swen2_tourplanner_server.repositories.TourRepository;
import at.technikum.swen2_tourplanner_server.services.ReportService;
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
    TourReport getReport(@PathVariable Long id) {
        return this.reportService.generateTourReport(id);
    }
}
