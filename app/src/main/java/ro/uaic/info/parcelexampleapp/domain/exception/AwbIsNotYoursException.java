package ro.uaic.info.parcelexampleapp.domain.exception;

public class AwbIsNotYoursException extends Exception {
    public AwbIsNotYoursException(String awbNumber) {
        super("AWB with number \"" + awbNumber + "\" is not yours!");
    }
}
