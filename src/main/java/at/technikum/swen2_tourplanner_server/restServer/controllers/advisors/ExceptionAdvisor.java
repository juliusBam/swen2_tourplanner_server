package at.technikum.swen2_tourplanner_server.restServer.controllers.advisors;

import at.technikum.swen2_tourplanner_server.exceptions.TourCreationErrorExc;
import at.technikum.swen2_tourplanner_server.exceptions.TourNotFoundExc;
import at.technikum.swen2_tourplanner_server.exceptions.TourUpdateErrorExc;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

//Advisor to overwrite the default behaviour of exceptions

@ControllerAdvice
public class ExceptionAdvisor {
    //region exception handling
    @ResponseBody
    @ExceptionHandler(TourNotFoundExc.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String tourNotFoundHandler(TourNotFoundExc ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(TourCreationErrorExc.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String cannotCreateTour(TourCreationErrorExc ex) {return ex.getMessage();}

    @ResponseBody
    @ExceptionHandler(TourUpdateErrorExc.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String cannotUpdateTour(TourCreationErrorExc ex) {
        return ex.getMessage();
    }
    //endregion
}
