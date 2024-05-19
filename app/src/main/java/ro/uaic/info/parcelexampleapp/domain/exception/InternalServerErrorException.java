package ro.uaic.info.parcelexampleapp.domain.exception;

public class InternalServerErrorException extends Exception {
    public InternalServerErrorException() {
        super("An error has occurred!");
    }
}
