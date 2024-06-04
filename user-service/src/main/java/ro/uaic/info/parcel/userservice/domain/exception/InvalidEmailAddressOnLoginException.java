package ro.uaic.info.parcel.userservice.domain.exception;

public class InvalidEmailAddressOnLoginException extends Exception{
    public InvalidEmailAddressOnLoginException() {
        super("Invalid email address!");
    }
}
