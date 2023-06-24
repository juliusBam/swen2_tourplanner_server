package at.technikum.swen2_tourplanner_server.BL;

import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.entities.TourLog;
import at.technikum.swen2_tourplanner_server.entities.enums.Rating;
import at.technikum.swen2_tourplanner_server.entities.enums.Vehicle;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.ReportGenerationException;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.ListNumberingType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class ReportGenerator {

    private final float headerFontSize = 32f;

    private final float PaddingValue = 20;

    public ReportGeneratorOutput generateTourReport(Tour tour) {

        //we operate with byte arrays, so that we can avoid having to store the file and delete it afterwards

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {

            try (PdfDocument pdf = new PdfDocument(new PdfWriter(byteArrayOutputStream));
                 Document doc = new Document(pdf);
            ) {

                doc.add(this.createTourBasicInfo(tour));

                doc.add(
                        new Paragraph("Tour logs")
                                .setFontSize(this.headerFontSize)
                );

                List tourLogsListForReport = new List(ListNumberingType.DECIMAL);

                java.util.List<TourLog> sortedLogs = new ArrayList<>(tour.getLogs());

                sortedLogs.sort(Comparator.comparing(TourLog::getTimeStamp));

                for (TourLog log : sortedLogs) {
                    java.util.Date tourLogDate = new Date(new Timestamp(log.getTimeStamp()).getTime());

                    ListItem item = new ListItem();

                    item.add(
                            new Paragraph(tourLogDate.toString()).setBold()
                    );
                    item.add(
                            new Paragraph(log.getComment())
                    );

                    Table tourLogDetailsTable = new Table(2);

                    tourLogDetailsTable.addCell(
                            this.createCell("Difficulty:", true)
                    );
                    tourLogDetailsTable.addCell(
                            this.createCell(log.getDifficulty().toString().toLowerCase(), true)
                    );

                    tourLogDetailsTable.addCell(
                            this.createCell("Rating:", true)
                    );
                    tourLogDetailsTable.addCell(
                            this.createCell(this.convertRating(log.getRating()), true)
                    );

                    tourLogDetailsTable.addCell(
                            this.createCell("Needed time (minutes):", true)
                    );
                    tourLogDetailsTable.addCell(
                            this.createCell(log.getTotalTimeMinutes().toString(), true)
                    );

                    item.add(tourLogDetailsTable);
                    item.setPaddingBottom(this.PaddingValue);

                    tourLogsListForReport.add(item);
                }

                doc.add(tourLogsListForReport);

            }

            //return has to happen afterwards we closed the doc and pdfwriter, but before the stream is closed
            return new ReportGeneratorOutput(
                    tour.getName().toLowerCase().replace(" ", "_"),
                    byteArrayOutputStream.toByteArray()
            );

        }
        catch (IOException e) {
            throw new ReportGenerationException(e.getMessage());
        }

    }

    public ReportGeneratorOutput generateSummarizeReport(java.util.List<Tour> allTours) {

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {

            try (PdfDocument pdf = new PdfDocument(new PdfWriter(byteArrayOutputStream));
                 Document doc = new Document(pdf);
            ) {

                doc.add(
                        new Paragraph("Summarized Report")
                                .setFontSize(50)
                                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                );

                List tourListForReport = new List();

                for (Tour tour : allTours) {

                    ListItem tourEntry = new ListItem();

                    tourEntry.add(this.createTourBasicInfo(tour));

                    tourEntry.add(new Paragraph("Summary from tour logs").setFontSize(26));

                    Table averageStatsTable = new Table(2);

                    averageStatsTable.addCell(this.createCell("Popularity:", true));
                    averageStatsTable.addCell(this.createCell(tour.getPopularity().toString(), true));

                    averageStatsTable.addCell(this.createCell("Average time (minutes):", true));
                    averageStatsTable.addCell(this.createCell(tour.getAvgTime().toString(), true));

                    averageStatsTable.addCell(this.createCell("Average difficulty:", true));
                    averageStatsTable.addCell(this.createCell(tour.getAvgDifficulty().toString(), true));

                    averageStatsTable.addCell(this.createCell("Average rating:", true));
                    averageStatsTable.addCell(this.createCell(tour.getAvgRating().toString(), true));

                    tourEntry.add(averageStatsTable);

                    tourListForReport.add(tourEntry);
                }

                doc.add(tourListForReport);

            }

            //return has to happen afterwards we closed the doc and pdfwriter, but before the stream is closed
            return new ReportGeneratorOutput(
                    "tourPlanner_summarizedReport",
                    byteArrayOutputStream.toByteArray()
            );

        }
        catch (IOException e) {
            throw new ReportGenerationException(e.getMessage());
        }
    }

    private Cell createCell(String cellContent, boolean transparent) {

        return new Cell(1,1)
                        .add(new Paragraph(cellContent))
                        .setBorder(transparent ? Border.NO_BORDER : null);

    }

    private Div createTourBasicInfo(Tour tour) throws IOException {

        Div tourContainer = new Div();
        //create title
        tourContainer.add(
                new Paragraph(tour.getName())
                        .setFontSize(this.headerFontSize)
        );

        //create desc
        tourContainer.add(
                new Paragraph(tour.getDescription())
        );

        //add stats table
        tourContainer.add(this.createTourStatsTable(tour));


        //fetch the image from the req url for the tour infos
        ImageData imageData = ImageDataFactory.create(tour.getRouteInformation());

        Image image = new Image(imageData);
        tourContainer.add(image);

        return tourContainer;

    }

    private Table createTourStatsTable(Tour tour) {

        Table tourInfoTable = new Table(2);

        tourInfoTable.addCell(this.createCell("Distance (km):", true));
        tourInfoTable.addCell(this.createCell(tour.getTourDistanceKilometers().toString(), true));

        tourInfoTable.addCell(this.createCell("Needed time in minutes:", true));

        Integer tourTimeInMinutes = (int) (tour.getEstimatedTimeSeconds() / 60);

        tourInfoTable.addCell(this.createCell(tourTimeInMinutes.toString(), true));

        tourInfoTable.addCell(this.createCell("Vehicle:", true));
        tourInfoTable.addCell(this.createCell(this.convertVehicle(tour.getVehicle()), true));

        return tourInfoTable;

    }

    private String convertVehicle(Vehicle vehicle) {

        switch (vehicle) {
            case BIKE -> {
                return "bike";
            }
            case CAR -> {
                return "car";
            }
            case WALK -> {
                return "hiking tour";
            }
            default -> {
                return "unknown vehicle";
            }
        }

    }

    private String convertRating(Rating rating) {

        switch (rating) {
            case BAD -> {
                return "negative";
            }
            case DECENT -> {
                return "neutral";
            }
            case GOOD -> {
                return "good";
            }
            default -> {
                return "unknown rating";
            }
        }

    }

}