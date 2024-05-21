package ro.uaic.info.parcelexampleapp.domain.exception;

public class IncorrectCredentialsException extends Exception {
    public IncorrectCredentialsException() {
        super("Email and/or password is incorrect!");
    }
}
