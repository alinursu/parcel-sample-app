package ro.uaic.info.parcel.paymentservice.domain.exception;

public class AwbAlreadyPayedException extends Exception {
    public AwbAlreadyPayedException(String awbNumber) {
        super("AWB with number \"" + awbNumber + "\" has already been payed!");
    }
}
