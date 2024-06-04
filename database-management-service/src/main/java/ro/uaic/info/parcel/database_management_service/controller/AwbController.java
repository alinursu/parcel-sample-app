package ro.uaic.info.parcel.database_management_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ro.uaic.info.parcel.database_management_service.api.AwbApi;
import ro.uaic.info.parcel.database_management_service.domain.Awb;
import ro.uaic.info.parcel.database_management_service.domain.AwbStatus;
import ro.uaic.info.parcel.database_management_service.domain.User;
import ro.uaic.info.parcel.database_management_service.domain.dto.AddAwbRequestDto;
import ro.uaic.info.parcel.database_management_service.service.AwbService;
import ro.uaic.info.parcel.database_management_service.service.UserService;

import java.util.Date;
import java.util.List;

@Controller
public class AwbController implements AwbApi {
    @Autowired
    private AwbService awbService;

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<List<Awb>> getAllAwbs() {
        return ResponseEntity.status(HttpStatus.OK).body(awbService.getAllAwbs());
    }

    @Override
    public ResponseEntity<Awb> getAwbByUniqueNumber(String number) {
        Awb awbById = awbService.getAwbByUniqueNumber(number);
        if (awbById != null) {
            return ResponseEntity.status(HttpStatus.OK).body(awbById);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @Override
    public ResponseEntity<Awb> addAwb(AddAwbRequestDto dto) {
        User userById = userService.getUserById(dto.getOwnerId());

        if (userById == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Awb awb = Awb.builder()
                .uniqueNumber(dto.getUniqueNumber())
                .shipmentAddress(dto.getShipmentAddress())
                .deliveryAddress(dto.getDeliveryAddress())
                .price(dto.getPrice())
                .owner(userById)
                .creationDate(new Date())
                .lastUpdateDate(new Date())
                .status(AwbStatus.AWAITING_PAYMENT)
                .build();

        awb = awbService.addOrUpdateAwb(awb);

        if (awb != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(awb);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @Override
    public ResponseEntity<Awb> updateAwb(Awb awb) {
        awb = awbService.addOrUpdateAwb(awb);

        if (awb != null) {
            return ResponseEntity.status(HttpStatus.OK).body(awb);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
