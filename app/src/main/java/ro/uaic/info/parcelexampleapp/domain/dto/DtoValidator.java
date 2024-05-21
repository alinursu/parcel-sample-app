package ro.uaic.info.parcelexampleapp.domain.dto;

import ro.uaic.info.parcelexampleapp.domain.exception.*;

import java.util.regex.Pattern;

public class DtoValidator {
    public static boolean isValid(UserRegisterDto dto) throws InvalidRegisterDtoException, InvalidEmailAddressOnRegisterException {
        if (dto == null) {
            throw new InvalidRegisterDtoException("Missing register details");
        }

        if (dto.getFirstName() == null || dto.getFirstName().trim().equals("")) {
            throw new InvalidRegisterDtoException("First name input is missing!");
        }

        if (dto.getLastName() == null || dto.getLastName().trim().equals("")) {
            throw new InvalidRegisterDtoException("Last name input is missing!");
        }

        if (dto.getEmail() == null || dto.getEmail().trim().equals("")) {
            throw new InvalidRegisterDtoException("Email is missing!");
        }

        if (dto.getPassword() == null || dto.getPassword().trim().equals("")) {
            throw new InvalidRegisterDtoException("Password is missing!");
        }

        if (!isEmailAddress(dto.getEmail())) {
            throw new InvalidEmailAddressOnRegisterException();
        }

        return true;
    }

    public static boolean isValid(UserLoginDto dto) throws InvalidLoginDtoException, InvalidEmailAddressOnLoginException {
        if (dto == null) {
            throw new InvalidLoginDtoException("Missing login details");
        }

        if (dto.getEmail() == null || dto.getEmail().trim().equals("")) {
            throw new InvalidLoginDtoException("Email is missing!");
        }

        if (dto.getPassword() == null || dto.getPassword().trim().equals("")) {
            throw new InvalidLoginDtoException("Password is missing!");
        }

        if (!isEmailAddress(dto.getEmail())) {
            throw new InvalidEmailAddressOnLoginException();
        }

        return true;
    }

    public static boolean isValid(CreateAwbDto dto) throws InvalidAwbDtoException {
        if (dto == null) {
            throw new InvalidAwbDtoException("Missing details.");
        }

        if (dto.getDeliveryAddress() == null || dto.getDeliveryAddress().trim().equals("")) {
            throw new InvalidAwbDtoException("Missing delivery address.");
        }

        if (dto.getShipmentAddress() == null || dto.getShipmentAddress().trim().equals("")) {
            throw new InvalidAwbDtoException("Missing shipping address.");
        }

        return true;
    }

    public static boolean isValid(UpdateAwbStatusDto dto) throws InvalidUpdateAwbDtoException {
        if(dto == null) {
            throw new InvalidUpdateAwbDtoException("Missing details");
        }

        return true;
    }

    private static boolean isEmailAddress(String email) {
        return Pattern.matches("^(.+)@(\\S+)\\.(\\w)+$", email);
    }
}
