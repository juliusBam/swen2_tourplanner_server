package at.technikum.swen2_tourplanner_server.helpers.validators;

public interface IValidator<T> {

    public void validateCreation(T entity) throws RuntimeException;

    //todo add a parameter for the stored entity
    public void validateUpdate(T entity) throws RuntimeException;

}
