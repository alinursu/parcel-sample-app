package ro.uaic.info.parcel.userservice.domain.exception;

public class IncorrectCredentialsException extends Exception {
    public IncorrectCredentialsException() {
        super("Email and/or password is incorrect!");
    }
}
