package ATP.Project.EziCall.exception;

public class TicketModificationNotAllowedException extends RuntimeException{
    public TicketModificationNotAllowedException(String message) {
        super(message);
    }

    public TicketModificationNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}
