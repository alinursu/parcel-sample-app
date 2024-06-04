package ro.uaic.info.parcel.userservice.domain.exception;

public class EmailAlreadyInUseException extends Exception {
    public EmailAlreadyInUseException(String email) {
        super("Email \"" + email + "\" is already tied to an account.");
    }
}
