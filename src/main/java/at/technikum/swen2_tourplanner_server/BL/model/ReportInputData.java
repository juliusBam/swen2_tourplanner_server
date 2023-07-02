package at.technikum.swen2_tourplanner_server.BL.model;

import at.technikum.swen2_tourplanner_server.entities.Tour;

public record ReportInputData(Tour tour, TourStatsModel tourStatsModel) {
}
