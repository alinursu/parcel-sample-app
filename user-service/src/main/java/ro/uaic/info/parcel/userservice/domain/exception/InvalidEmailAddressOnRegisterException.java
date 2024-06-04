package ro.uaic.info.parcel.userservice.domain.exception;

public class InvalidEmailAddressOnRegisterException extends Exception{
    public InvalidEmailAddressOnRegisterException() {
        super("Invalid email address!");
    }
}
