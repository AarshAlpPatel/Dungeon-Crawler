package main.backend.exceptions;

public class NoNameException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public NoNameException(String message) {
        super(message);
    }
}