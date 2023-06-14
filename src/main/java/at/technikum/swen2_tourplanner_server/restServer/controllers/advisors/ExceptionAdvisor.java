package at.technikum.swen2_tourplanner_server.restServer.controllers.advisors;

import at.technikum.swen2_tourplanner_server.exceptions.RecordCreationErrorExc;
import at.technikum.swen2_tourplanner_server.exceptions.RecordNotFoundExc;
import at.technikum.swen2_tourplanner_server.exceptions.RecordUpdateErrorExc;
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
    @ExceptionHandler(RecordNotFoundExc.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String tourNotFoundHandler(RecordNotFoundExc ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(RecordCreationErrorExc.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String cannotCreateTour(RecordCreationErrorExc ex) {return ex.getMessage();}

    @ResponseBody
    @ExceptionHandler(RecordUpdateErrorExc.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String cannotUpdateTour(RecordUpdateErrorExc ex) {
        return ex.getMessage();
    }
    //endregion
}
