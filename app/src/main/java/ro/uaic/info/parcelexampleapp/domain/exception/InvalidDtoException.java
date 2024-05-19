package ro.uaic.info.parcelexampleapp.domain.exception;

public class InvalidDtoException extends Exception {
    public InvalidDtoException(String action) {
        super("Received invalid dto for \"" + action + "\" action!");
    }
}
