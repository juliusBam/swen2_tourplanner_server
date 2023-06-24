package at.technikum.swen2_tourplanner_server.BL;

public class ReportGeneratorOutput {
    private final String reportName;

    private final byte[] reportContent;

    public ReportGeneratorOutput(String reportName, byte[] reportContent) {
        this.reportName = reportName;
        this.reportContent = reportContent;
    }

    public String getReportName() {
        return reportName;
    }

    public byte[] getReportContent() {
        return reportContent;
    }
}
