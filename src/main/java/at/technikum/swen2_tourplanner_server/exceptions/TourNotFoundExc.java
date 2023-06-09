package at.technikum.swen2_tourplanner_server.exceptions;

public class TourNotFoundExc extends RuntimeException {

    public TourNotFoundExc(Long id) {
        super("Could not find tour: " + id);
    }

}