package at.technikum.swen2_tourplanner_server.BL.validators;

import at.technikum.swen2_tourplanner_server.dto.TourDto;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.RecordCreationErrorExc;
import at.technikum.swen2_tourplanner_server.restServer.exceptions.RecordUpdateErrorExc;

public class TourValidator implements IValidator<TourDto> {

    @Override
    public void validateCreation(TourDto tourDto) throws RuntimeException {

        if (tourDto == null) {
            throw new RecordCreationErrorExc("Tour model is null");
        }

        if (tourDto.getStart().equals(tourDto.getEnd())) {
            throw new RecordCreationErrorExc("Tour start and tour end have to be different");
        }

    }

    //todo add a method to check if the "version" is valid
    @Override
    public void validateUpdate(TourDto tourDto) throws RuntimeException {

        try {
            this.validateCreation(tourDto);
        } catch (RecordCreationErrorExc e) {
            throw new RecordUpdateErrorExc(e.getMessage());
        }

        if (tourDto.getId() == null) {
            throw new RecordUpdateErrorExc("Tour id has to be set");
        }

    }
}
