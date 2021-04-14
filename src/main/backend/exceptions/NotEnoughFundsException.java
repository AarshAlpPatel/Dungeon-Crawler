package main.backend.exceptions;

public class NotEnoughFundsException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public NotEnoughFundsException(String message) {
        super(message);
    }
}
