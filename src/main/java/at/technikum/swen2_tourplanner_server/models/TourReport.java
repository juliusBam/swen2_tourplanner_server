package at.technikum.swen2_tourplanner_server.models;

public class TourReport {
    private final String base64Report;

    public TourReport(String base64Report) {
        this.base64Report = base64Report;
    }

    public String getBase64Report() {
        return this.base64Report;
    }
}
