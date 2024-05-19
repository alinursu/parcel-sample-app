package ro.uaic.info.parcelexampleapp.domain.exception;

public class AwbAlreadyPayedException extends Exception {
    public AwbAlreadyPayedException(String awbNumber) {
        super("AWB with number \"" + awbNumber + "\" has already been payed!");
    }
}
