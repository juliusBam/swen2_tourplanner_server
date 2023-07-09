package at.technikum.swen2_tourplanner_server.BL;

import at.technikum.swen2_tourplanner_server.AppPropertySource;
import at.technikum.swen2_tourplanner_server.BL.model.ReportGeneratorOutput;
import at.technikum.swen2_tourplanner_server.BL.model.ReportInputData;
import at.technikum.swen2_tourplanner_server.BL.model.TourStatsModel;
import at.technikum.swen2_tourplanner_server.Logging;
import at.technikum.swen2_tourplanner_server.MapQuestSource;
import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.entities.TourLog;
import at.technikum.swen2_tourplanner_server.entities.enums.Vehicle;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.ReportGenerationException;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.ListNumberingType;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class ReportGenerator extends Logging {

    private final float headerFontSize = 32f;

    private final float PaddingValue = 20;

    private final String apiKey;

    private final String baseUrl;

    public ReportGenerator(MapQuestSource mapQuestSource) {
        super();
        this.apiKey = mapQuestSource.getApiKey();
        this.baseUrl = mapQuestSource.getApiUrl();
    }


    public ReportGeneratorOutput generateTourReport(ReportInputData tourInputData, String sessionId) {

        //we operate with byte arrays, so that we can avoid having to store the file and delete it afterwards

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {

            try (PdfDocument pdf = new PdfDocument(new PdfWriter(byteArrayOutputStream)); Document doc = new Document(pdf, PageSize.A4)) {
                doc.setMargins(30F, 30F, 30F, 30F);

                addHeading(doc);

                Paragraph p = new Paragraph("Detail report");
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss");
                String nowFormatted = now.format(formatter);
                p.add(" generated on " + nowFormatted + ".");
                doc.add(p);

                doc.add(this.createTourBasicInfo(tourInputData.tour(), sessionId));

                if (tourInputData.tour().getLogs().size() != 0) {

                    doc.add(new Paragraph("Statistics from tour logs:").setFontSize(18f));

                    doc.add(this.createTourAverageStatsTable(tourInputData.tourStatsModel()).setMarginBottom(15));
                    doc.add(new AreaBreak());

                    doc.add(new Paragraph("Tour log entries:").setFontSize(18f));

                    doc.add(this.createTourLogsEntries(tourInputData.tour()));

                }
                else {
                    doc.add(new Paragraph("This tour does not have any associated log entries."));
                }

            }

            //return has to happen afterwards we closed the doc and pdfwriter, but before the stream is closed
            return new ReportGeneratorOutput(tourInputData.tour().getName().toLowerCase().replace(" ", "_"), byteArrayOutputStream.toByteArray());

        } catch (IOException e) {
            logger.error(String.format("Error generating tour report for tour: [{%d}], error: [{%s}]", tourInputData.tour().getId(), e.getMessage()));
            throw new ReportGenerationException(e.getMessage());
        }

    }

    private void addHeading(Document doc) {
        Div titleContainer = new Div();
        Paragraph titleParagraph = new Paragraph();
        titleParagraph.add(new Text("TourPlanner - Report").setFontSize(this.headerFontSize));

        titleContainer.add(titleParagraph);
        titleContainer.setTextAlignment(TextAlignment.CENTER);
        doc.add(titleContainer);

        SolidLine line = new SolidLine(2f);
        line.setColor(ColorConstants.GRAY);
        LineSeparator ls = new LineSeparator(line);
        ls.setWidth(UnitValue.createPercentValue(75));
        ls.setMarginTop(1);
        ls.setMarginBottom(25);
        ls.setHorizontalAlignment(HorizontalAlignment.CENTER);
        doc.add(ls);
    }

    public ReportGeneratorOutput generateSummarizeReport(java.util.List<ReportInputData> allTours) {

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {

            try (PdfDocument pdf = new PdfDocument(new PdfWriter(byteArrayOutputStream)); Document doc = new Document(pdf);) {
                doc.setMargins(30F, 30F, 30F, 30F);

                addHeading(doc);

                Paragraph p = new Paragraph("Summary report of all tours");
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss");
                String nowFormatted = now.format(formatter);
                p.add(", generated on " + nowFormatted + ".");
                doc.add(p);

                List tourListForReport = new List();

                for (ReportInputData inputDataEntry : allTours) {
                    ListItem tourEntry = new ListItem();

                    SolidLine line = new SolidLine(1f);
                    line.setColor(ColorConstants.BLACK);
                    LineSeparator ls = new LineSeparator(line);
                    ls.setWidth(UnitValue.createPercentValue(100));
                    ls.setMarginTop(10);
                    ls.setMarginBottom(5);

                    tourEntry.add(ls);

                    tourEntry.add(this.createTourBasicInfo(inputDataEntry.tour(), "").setMarginTop(15));

                    if (!inputDataEntry.tour().getLogs().isEmpty()) {
                        tourEntry.add(new Paragraph("Statistics from tour logs:"));

                        Table averageStatsTable = this.createTourAverageStatsTable(inputDataEntry.tourStatsModel());

                        tourEntry.add(averageStatsTable);
                    } else {
                        tourEntry.add(new Paragraph("This tour does not have any associated log entries."));
                    }

                    tourListForReport.add(tourEntry);
                }

                doc.add(tourListForReport);
            }

            //return has to happen afterwards we closed the doc and pdfwriter, but before the stream is closed
            return new ReportGeneratorOutput("tourPlanner_summarizedReport", byteArrayOutputStream.toByteArray());

        } catch (IOException e) {
            logger.error(String.format("Error generating summarize report: [{%s}]", e.getMessage()));
            throw new ReportGenerationException(e.getMessage());
        }
    }

    private Cell createCell(String cellContent) {

        return new Cell(1, 1).add(new Paragraph(cellContent)).setBorder(new SolidBorder(ColorConstants.GRAY, 1, 0.5F));

    }

    private Div createTourBasicInfo(Tour tour, String imageSessionId) throws IOException {

        Div tourContainer = new Div();
        //create title
        Paragraph p = new Paragraph("Tour name: ");
        p.add(new Text(tour.getName()).setBold());

        tourContainer.add(p);

        //create desc
        tourContainer.add(new Paragraph("Tour description: " + tour.getDescription()).setMarginBottom(15));

        //add stats table
        tourContainer.add(this.createTourStatsTable(tour));

        //fetch the image from the req url for the tour info
        if (!imageSessionId.isEmpty()) {
            ImageData imageData = ImageDataFactory.create(this.createTourInformation(imageSessionId, tour.getVehicle()));

            Image image = new Image(imageData);
            image.setMarginTop(15);
            image.setMarginBottom(15);
            image.setHorizontalAlignment(HorizontalAlignment.CENTER);
            image.setBorder(new SolidBorder(ColorConstants.GRAY, 2));
            image.setWidth(UnitValue.createPercentValue(85));
            tourContainer.add(image);
        }
        return tourContainer;

    }

    private Table createTourStatsTable(Tour tour) {

        Table tourInfoTable = new Table(2);
        tourInfoTable.setWidth(UnitValue.createPercentValue(100));

        tourInfoTable.addCell(this.createCell("Distance:").setWidth(UnitValue.createPercentValue(30)));
        tourInfoTable.addCell(this.createCell(String.format("%,.2f", tour.getTourDistanceKilometers()) + " km"));

        tourInfoTable.addCell(this.createCell("Estimated time:"));

        Long tourDurationSeconds = tour.getEstimatedTimeSeconds();
        String formattedDuration = String.format("%d:%02d:%02d", tourDurationSeconds / 3600, (tourDurationSeconds % 3600) / 60, (tourDurationSeconds % 60));

        tourInfoTable.addCell(this.createCell(formattedDuration + " (H:MM:SS)"));

        tourInfoTable.addCell(this.createCell("Tour type:"));
        tourInfoTable.addCell(this.createCell(this.convertVehicle(tour.getVehicle())));

        return tourInfoTable;

    }

    private String convertVehicle(Vehicle vehicle) {

        switch (vehicle) {
            case BIKE -> {
                return "Bicycle tour";
            }
            case CAR -> {
                return "Car tour";
            }
            case WALK -> {
                return "Hiking tour";
            }
            default -> {
                return "Tour type unknown";
            }
        }

    }

    private String createTourInformation(String sessionId, Vehicle type) {
        String colorParam;
        switch (type) {
            case CAR -> colorParam = "routeColor=255,0,0";
            case BIKE -> colorParam = "routeColor=0,255,0";
            case WALK -> colorParam = "routeColor=0,0,255";
            default -> colorParam = "routeColor=0,0,0";
        }
        String sessionParam = "session=" + sessionId;
        String keyParam = "key=" + this.apiKey;
        String sizeParam = "size=600,400@2x";

        return this.baseUrl + "?" + keyParam + "&" + sizeParam + "&" + sessionParam + "&" + colorParam;
    }

    private List createTourLogsEntries(Tour tour) {

        List tourLogsListForReport = new List(ListNumberingType.DECIMAL);

        java.util.List<TourLog> sortedLogs = new ArrayList<>(tour.getLogs());

        sortedLogs.sort(Comparator.comparing(TourLog::getTimeStamp));

        for (TourLog log : sortedLogs) {

            Date tourLogDate = new Date(log.getTimeStamp() * 1000L);
            String formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(tourLogDate);

            ListItem item = new ListItem();

            item.add(new Paragraph(formattedDate).setBold());
            item.add(new Paragraph("Comment: " + log.getComment()));

            Table tourLogDetailsTable = new Table(2);
            tourLogDetailsTable.setWidth(UnitValue.createPercentValue(100));

            tourLogDetailsTable.addCell(this.createCell("Difficulty:").setWidth(UnitValue.createPercentValue(30)));
            tourLogDetailsTable.addCell(this.createCell(log.getDifficulty().toString().toLowerCase()));

            tourLogDetailsTable.addCell(this.createCell("Rating:"));
            tourLogDetailsTable.addCell(this.createCell(log.getRating().toString()));

            tourLogDetailsTable.addCell(this.createCell("Time taken:"));
            tourLogDetailsTable.addCell(this.createCell(String.format("%d:%02d (H:MM)", log.getTotalTimeMinutes() / 60, log.getTotalTimeMinutes() % 60)));

            item.add(tourLogDetailsTable);
            item.setPaddingBottom(this.PaddingValue);

            tourLogsListForReport.add(item);
        }

        return tourLogsListForReport;
    }

    private Table createTourAverageStatsTable(TourStatsModel tourStatsModel) {

        Table averageStatsTable = new Table(2);
        averageStatsTable.setWidth(UnitValue.createPercentValue(100));

        averageStatsTable.addCell(this.createCell("Popularity:").setWidth(UnitValue.createPercentValue(30)));
        averageStatsTable.addCell(this.createCell(
                String.format("#%d", tourStatsModel.popularity())
        ));

        averageStatsTable.addCell(this.createCell("Average time taken:"));
        long avgTimeSeconds = Math.round(tourStatsModel.avgTime() * 60);
        averageStatsTable.addCell(this.createCell(
                String.format("%d:%02d:%02d (H:MM:SS)", avgTimeSeconds / 3600, avgTimeSeconds % 3600 / 60, avgTimeSeconds % 60)
        ));

        averageStatsTable.addCell(this.createCell("Average difficulty:"));
        averageStatsTable.addCell(this.createCell(
                String.format("%,.2f", tourStatsModel.avgDifficulty())
        ));

        averageStatsTable.addCell(this.createCell("Average rating:"));
        averageStatsTable.addCell(this.createCell(
                String.format("%,.2f", tourStatsModel.avgRating())
        ));

        averageStatsTable.addCell(this.createCell("Child friendliness:"));
        averageStatsTable.addCell(this.createCell(
                String.format("%,.2f", tourStatsModel.childFriendliness())
        ));

        return averageStatsTable;
    }

}
