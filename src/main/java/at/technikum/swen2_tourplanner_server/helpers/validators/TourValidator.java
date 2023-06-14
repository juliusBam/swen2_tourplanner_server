package at.technikum.swen2_tourplanner_server.helpers.validators;

import at.technikum.swen2_tourplanner_server.dto.requests.CreateTourRequestModel;
import at.technikum.swen2_tourplanner_server.exceptions.RecordCreationErrorExc;
import at.technikum.swen2_tourplanner_server.exceptions.RecordUpdateErrorExc;

public class TourValidator implements IValidator<CreateTourRequestModel> {

    @Override
    public void validateCreation(CreateTourRequestModel tourRequestModel) throws RuntimeException {

        if (tourRequestModel == null) {
            throw new RecordCreationErrorExc("Tour model is null");
        }

        if (tourRequestModel.getStart().getCoordinate().equals(tourRequestModel.getEnd().getCoordinate())
            || tourRequestModel.getStart().getLabel().equals(tourRequestModel.getEnd().getLabel())) {
            throw new RecordCreationErrorExc("Tour start and tour end have to be different");
        }

    }

    @Override
    public void validateUpdate(CreateTourRequestModel tourRequestModel) throws RuntimeException {

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
