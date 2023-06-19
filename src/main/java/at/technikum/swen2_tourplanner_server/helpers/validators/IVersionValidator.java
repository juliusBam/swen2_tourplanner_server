package at.technikum.swen2_tourplanner_server.helpers.validators;

public interface IVersionValidator<T> {

    void validateOperation(T entity);

}
