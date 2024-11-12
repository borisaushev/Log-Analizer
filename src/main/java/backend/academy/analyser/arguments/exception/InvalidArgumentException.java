package backend.academy.analyser.arguments.exception;

public class InvalidArgumentException extends Exception {
    public InvalidArgumentException(String message) {
        super(message);
    }

    public InvalidArgumentException(String message, Exception exc) {
        super(message, exc);
    }
}
