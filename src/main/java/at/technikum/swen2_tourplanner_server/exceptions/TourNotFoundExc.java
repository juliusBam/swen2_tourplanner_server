package at.technikum.swen2_tourplanner_server.exceptions;

public class TourNotFoundExc extends RuntimeException {

    public TourNotFoundExc(String msg) {
        super(msg);
    }

}