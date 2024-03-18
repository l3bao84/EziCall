package ATP.Project.EziCall.exception;

public class UnauthorizedNoteCreationException extends RuntimeException{
    public UnauthorizedNoteCreationException(String message) {
        super(message);
    }

    public UnauthorizedNoteCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
