package at.technikum.swen2_tourplanner_server.BL.validators;

public interface IVersionValidator<T> {

    void validateOperation(T entity);

}
