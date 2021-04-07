package main.backend.exceptions;

public class IllegalDropException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public IllegalDropException(String message) {
        super(message);
    }
}
