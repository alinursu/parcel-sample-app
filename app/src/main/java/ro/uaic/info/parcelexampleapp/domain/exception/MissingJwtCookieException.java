package ro.uaic.info.parcelexampleapp.domain.exception;

public class MissingJwtCookieException extends Exception {
    public MissingJwtCookieException() {
        super("Missing JWT cookie!");
    }
}
