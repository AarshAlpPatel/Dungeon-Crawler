package main.backend.exceptions;

public class TooManyWeapons extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TooManyWeapons(String message) {
        super(message);
    }
}
