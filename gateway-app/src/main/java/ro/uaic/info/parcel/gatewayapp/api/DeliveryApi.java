package ro.uaic.info.parcel.gatewayapp.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.parcel.database_management_service.domain.DeliveryRoute;
import ro.uaic.info.parcel.deliveryservice.domain.dto.UpdateAwbStatusDto;
import ro.uaic.info.parcel.deliveryservice.domain.exception.AwbDoesNotExistException;
import ro.uaic.info.parcel.deliveryservice.domain.exception.InvalidUpdateAwbDtoException;
import ro.uaic.info.parcel.gatewayapp.domain.exception.MissingJwtCookieException;

@RequestMapping("/delivery")
public interface DeliveryApi {
    @GetMapping
    ResponseEntity<DeliveryRoute> getTodayRoute(HttpServletRequest request) throws MissingJwtCookieException;

    @PutMapping("/awb/{awbNumber}")
    ResponseEntity<String> updateAwbStatus(@PathVariable String awbNumber, @RequestBody UpdateAwbStatusDto dto, HttpServletRequest request) throws AwbDoesNotExistException, InvalidUpdateAwbDtoException, MissingJwtCookieException;
}
