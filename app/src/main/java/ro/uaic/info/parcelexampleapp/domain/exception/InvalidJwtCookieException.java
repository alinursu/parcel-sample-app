package ro.uaic.info.parcelexampleapp.domain.exception;

public class InvalidJwtCookieException extends Exception {
    public InvalidJwtCookieException() {
        super("Invalid JWT cookie!");
    }
}
