package ro.uaic.info.parcel.deliveryservice.domain.dto;

import ro.uaic.info.parcel.deliveryservice.domain.exception.*;

public class DtoValidator {
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
}
