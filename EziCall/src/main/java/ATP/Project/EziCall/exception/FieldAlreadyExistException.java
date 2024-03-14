package ATP.Project.EziCall.exception;

public class FieldAlreadyExistException extends RuntimeException{

    public FieldAlreadyExistException(String message) {
        super(message);
    }

    public FieldAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
