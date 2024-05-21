package ro.uaic.info.parcelexampleapp.domain.exception;

public class InvalidEmailAddressOnLoginException extends Exception{
    public InvalidEmailAddressOnLoginException() {
        super("Invalid email address!");
    }
}
