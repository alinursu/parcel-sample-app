package ro.uaic.info.parcel.deliveryservice.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ro.uaic.info.parcel.database_management_service.domain.Awb;
import ro.uaic.info.parcel.deliveryservice.api.AwbApi;
import ro.uaic.info.parcel.deliveryservice.domain.dto.CreateAwbDto;
import ro.uaic.info.parcel.deliveryservice.domain.exception.UserNotLoggedInException;
import ro.uaic.info.parcel.deliveryservice.service.AwbService;

import java.util.List;

@Controller
public class AwbController implements AwbApi {
    @Autowired
    private AwbService awbService;

    @Override
    public ResponseEntity<List<Awb>> getAwbsOfUser(HttpServletRequest request) throws Exception {
        String jwtToken = request.getHeader("Authorization");
        if (jwtToken == null) {
            throw new UserNotLoggedInException();
        }

        return ResponseEntity.ok(awbService.getAllAwbsOfUser(jwtToken));
    }

    @Override
    public ResponseEntity<List<Awb>> getAllNonDeliveredAwbs() throws Exception {
        return ResponseEntity.ok(awbService.getAllNonDeliveredAwbs());
    }

    @Override
    public ResponseEntity<Awb> getAwbOfUserByUniqueNumber(HttpServletRequest request, String uniqueNumber) throws Exception {
        String jwtToken = request.getHeader("Authorization");
        if (jwtToken == null) {
            throw new UserNotLoggedInException();
        }

        return ResponseEntity.ok(awbService.getAwbOfUserWithUniqueNumber(jwtToken, uniqueNumber));
    }

    @Override
    public ResponseEntity<Awb> createAwb(CreateAwbDto dto, HttpServletRequest request) throws Exception {
        String jwtToken = request.getHeader("Authorization");
        if (jwtToken == null) {
            throw new UserNotLoggedInException();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(awbService.addAwb(jwtToken, dto));
    }
}
