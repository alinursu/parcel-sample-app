package ro.uaic.info.parcelexampleapp.api;

import ro.uaic.info.parcelexampleapp.domain.DeliveryRoute;
import ro.uaic.info.parcelexampleapp.domain.dto.UpdateAwbStatusDto;
import ro.uaic.info.parcelexampleapp.domain.exception.AwbDoesNotExistException;
import ro.uaic.info.parcelexampleapp.domain.exception.InvalidDtoException;
import ro.uaic.info.parcelexampleapp.domain.exception.InvalidJwtCookieException;
import ro.uaic.info.parcelexampleapp.domain.exception.MissingJwtCookieException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/delivery")
public interface DeliveryApi {
    @GetMapping
    ResponseEntity<DeliveryRoute> getTodayRoute(HttpServletRequest request) throws InvalidJwtCookieException, MissingJwtCookieException;

    @PutMapping("/awb/{awbNumber}")
    ResponseEntity<String> updateAwbStatus(@PathVariable String awbNumber, @RequestBody UpdateAwbStatusDto dto) throws AwbDoesNotExistException, InvalidDtoException;
}
