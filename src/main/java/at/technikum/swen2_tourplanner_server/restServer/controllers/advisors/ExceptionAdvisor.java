package at.technikum.swen2_tourplanner_server.restServer.controllers.advisors;

import at.technikum.swen2_tourplanner_server.restServer.exceptions.RecordCreationErrorExc;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.RecordNotFoundExc;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.RecordUpdateErrorExc;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.ReportGenerationException;
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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String entityNotFound(RecordNotFoundExc ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(RecordCreationErrorExc.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String cannotCreateEntity(RecordCreationErrorExc ex) {return ex.getMessage();}

    @ResponseBody
    @ExceptionHandler(RecordUpdateErrorExc.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String cannotUpdateEntity(RecordUpdateErrorExc ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ReportGenerationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String errorGeneratingReport(ReportGenerationException ex) {
        return ex.getMessage();
    }
    //endregion
}
