package at.technikum.swen2_tourplanner_server.BL;

import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.ReportGenerationException;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ReportGenerator {

    private final float headerFontSize = 32f;

    private final float defaultTextFontSize = 15f;

    //todo create the pdf and send it back to the client

    public byte[] generateTourReport(Tour tour) {

        //we operate with byte arrays, so that we can avoid having to store the file and delete it afterwards

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {

            try (PdfDocument pdf = new PdfDocument(new PdfWriter(byteArrayOutputStream));
                 Document doc = new Document(pdf);
            ) {

                doc.add(
                        new Paragraph(tour.getName())
                                .setFontSize(this.headerFontSize)
                );

                doc.add(
                        new Paragraph(tour.getDescription())
                                .setFontSize(this.defaultTextFontSize)
                );

                //fetch the image from the req url for the tour info
                //causes a long delay
                ImageData imageData = ImageDataFactory.create("https://www.mapquestapi.com/staticmap/v5/map?key=CMh8YgwyqDEwVfPW2IDoxhJysaAMZPTG&start=Wien&end=Innsbruck");

                Image image = new Image(imageData);
                doc.add(image);

                doc.add(
                        new Paragraph("Tour logs")
                                .setFontSize(this.headerFontSize)
                );

                //todo "print" the tour logs

                List list = new List();

                tour.getLogs().forEach((tourLog) -> {
                    list.add(new ListItem(tourLog.getComment()));
                });

                doc.add(list);

            }

            return byteArrayOutputStream.toByteArray();

        }
        catch (IOException e) {
            throw new ReportGenerationException(e.getMessage());
        }

    }

    public String generateSummarizeReport(Tour tour) {

        try (PdfDocument pdf = new PdfDocument(new PdfWriter("cool.pdf")) ) {

            //todo get the tour logs to calculate:
            // - average time
            // - average distance
            // - average rating
            // - print default tour info
            Document doc = new Document(pdf);

            doc.add(new Paragraph(tour.getName()).setFontSize(this.headerFontSize));

            doc.add(new Paragraph(tour.getDescription()));

            ImageData imageData = ImageDataFactory.create(tour.getRouteInformation());
            Image image = new Image(imageData);
            doc.add(image);

            doc.add(new Paragraph("Stats from tour logs").setFontSize(this.headerFontSize));

            doc.add(new Paragraph("Average difficulty"));
            doc.add(new Paragraph(tour.getAvgDifficulty().toString()));


        } catch (IOException e) {
            throw new ReportGenerationException(e.getMessage());
        }

        return "I am a cool tour summarize report";
    }

}
