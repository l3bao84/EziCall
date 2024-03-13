package ATP.Project.EziCall.exception;

public class InvalidFormatException extends RuntimeException{
    public InvalidFormatException(String message) {
        super(message);
    }

    public InvalidFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
