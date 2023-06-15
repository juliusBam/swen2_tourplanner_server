package at.technikum.swen2_tourplanner_server.helpers.validators;

import at.technikum.swen2_tourplanner_server.dto.requests.TourRequestModel;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.RecordCreationErrorExc;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.RecordUpdateErrorExc;

public class TourValidator implements IValidator<TourRequestModel> {

    @Override
    public void validateCreation(TourRequestModel tourRequestModel) throws RuntimeException {

        if (tourRequestModel == null) {
            throw new RecordCreationErrorExc("Tour model is null");
        }

        if (tourRequestModel.getStart().equals(tourRequestModel.getEnd())) {
            throw new RecordCreationErrorExc("Tour start and tour end have to be different");
        }

    }

    //todo add a method to check if the "version" is valid
    @Override
    public void validateUpdate(TourRequestModel tourRequestModel) throws RuntimeException {

        try {
            this.validateCreation(tourRequestModel);
        } catch (RecordCreationErrorExc e) {
            throw new RecordUpdateErrorExc(e.getMessage());
        }

        if (tourRequestModel.getId() == null) {
            throw new RecordUpdateErrorExc("Tour id has to be set");
        }

    }
}
