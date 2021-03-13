package main.backend.exceptions;

public class WallCollision extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public WallCollision(String s) {
        super(s);
    }
}
