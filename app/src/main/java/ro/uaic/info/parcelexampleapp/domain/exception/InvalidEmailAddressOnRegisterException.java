package ro.uaic.info.parcelexampleapp.domain.exception;

public class InvalidEmailAddressOnRegisterException extends Exception{
    public InvalidEmailAddressOnRegisterException() {
        super("Invalid email address!");
    }
}
