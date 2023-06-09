package at.technikum.swen2_tourplanner_server.exceptions;

public class TourUpdateErrorExc extends RuntimeException {

    TourUpdateErrorExc(String msg) {
        super(msg);
    }
}
