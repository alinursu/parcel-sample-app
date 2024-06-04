package ro.uaic.info.parcel.deliveryservice.domain.exception;

public class AwbDoesNotExistException extends Exception {
    public AwbDoesNotExistException(String awbNumber) {
        super("An AWB with number \"" + awbNumber + "\" does not exist!");
    }
}
