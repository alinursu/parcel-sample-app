package ro.uaic.info.parcelexampleapp.domain.exception;

public class IncorrectCredentialsException extends Exception {
    public IncorrectCredentialsException() {
        super("Invalid email/password!");
    }
}
