package main.backend.exceptions;

public class TooManyPotions extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TooManyPotions(String message) {
        super(message);
    }
}
