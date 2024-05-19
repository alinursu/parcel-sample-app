package ro.uaic.info.parcelexampleapp.domain.exception;

public class EmailAlreadyInUseException extends Exception {
    public EmailAlreadyInUseException(String email) {
        super("Email \"" + email + "\" is already tied to an account.");
    }
}
