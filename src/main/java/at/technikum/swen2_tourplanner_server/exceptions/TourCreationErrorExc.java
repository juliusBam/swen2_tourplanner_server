package at.technikum.swen2_tourplanner_server.exceptions;

public class TourCreationErrorExc extends RuntimeException {

    public TourCreationErrorExc(String name) {
        super("Error while creating tour: " + name);
    }

}
