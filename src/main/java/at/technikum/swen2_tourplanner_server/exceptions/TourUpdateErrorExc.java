package at.technikum.swen2_tourplanner_server.exceptions;

public class TourUpdateErrorExc extends RuntimeException {

    TourUpdateErrorExc(Long id) {
        super("Error while updating tour: " + id);
    }
}
