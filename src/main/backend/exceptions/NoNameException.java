package main.backend.exceptions;

public class NoNameException extends RuntimeException {
    public NoNameException(String message) {
        super(message);
    }
}
