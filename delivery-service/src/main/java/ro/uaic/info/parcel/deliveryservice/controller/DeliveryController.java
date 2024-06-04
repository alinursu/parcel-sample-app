package ro.uaic.info.parcel.deliveryservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ro.uaic.info.parcel.database_management_service.domain.Awb;
import ro.uaic.info.parcel.database_management_service.domain.DeliveryRoute;
import ro.uaic.info.parcel.deliveryservice.api.DeliveryApi;
import ro.uaic.info.parcel.deliveryservice.domain.dto.DtoValidator;
import ro.uaic.info.parcel.deliveryservice.domain.dto.UpdateAwbStatusDto;
import ro.uaic.info.parcel.deliveryservice.service.AwbService;
import ro.uaic.info.parcel.deliveryservice.service.DeliveryService;

import java.util.List;

@Controller
public class DeliveryController implements DeliveryApi {
    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private AwbService awbService;

    @Override
    public ResponseEntity<DeliveryRoute> getTodayRoute(HttpServletRequest request) throws Exception {
        String jwtToken = request.getHeader("Authorization");
        if (jwtToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok(deliveryService.getDeliveryRouteForUser(jwtToken));
    }

    @Override
    public ResponseEntity<List<DeliveryRoute>> getAllRoutesForToday() throws Exception {
        return ResponseEntity.ok(deliveryService.getAllDeliveryRoutesForToday());
    }

    @Override
    public ResponseEntity<Awb> updateAwbStatus(String awbNumber, UpdateAwbStatusDto dto) throws Exception {
        DtoValidator.isValid(dto);
        return ResponseEntity.ok(awbService.updateAwbStatus(awbNumber, dto.getNewStatus()));
    }
}
