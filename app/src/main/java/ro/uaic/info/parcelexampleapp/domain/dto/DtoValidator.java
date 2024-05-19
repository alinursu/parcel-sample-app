package ro.uaic.info.parcelexampleapp.domain.dto;

public class DtoValidator {
    public static boolean isValid(UserRegisterDto dto) {
        if (dto == null) {
            return false;
        }

        if (dto.getFirstName() == null || dto.getFirstName().trim().equals("")) {
            return false;
        }

        if (dto.getLastName() == null || dto.getLastName().trim().equals("")) {
            return false;
        }

        if (dto.getEmail() == null || dto.getEmail().trim().equals("")) {
            return false;
        }

        if (dto.getPassword() == null || dto.getPassword().trim().equals("")) {
            return false;
        }

        return true;
    }

    public static boolean isValid(UserLoginDto dto) {
        if (dto == null) {
            return false;
        }

        if (dto.getEmail() == null || dto.getEmail().trim().equals("")) {
            return false;
        }

        if (dto.getPassword() == null || dto.getPassword().trim().equals("")) {
            return false;
        }

        return true;
    }

    public static boolean isValid(CreateAwbDto dto) {
        if (dto == null) {
            return false;
        }

        if (dto.getDeliveryAddress() == null || dto.getDeliveryAddress().trim().equals("")) {
            return false;
        }

        if (dto.getShipmentAddress() == null || dto.getShipmentAddress().trim().equals("")) {
            return false;
        }

        return true;
    }

    public static boolean isValid(UpdateAwbStatusDto dto) {
        return dto != null;
    }
}
