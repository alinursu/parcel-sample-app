package ro.uaic.info.parcel.gatewayapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ro.uaic.info.parcel.database_management_service.domain.Awb;
import ro.uaic.info.parcel.database_management_service.domain.DeliveryRoute;
import ro.uaic.info.parcel.deliveryservice.domain.dto.UpdateAwbStatusDto;
import ro.uaic.info.parcel.deliveryservice.domain.exception.AwbDoesNotExistException;
import ro.uaic.info.parcel.deliveryservice.domain.exception.InvalidUpdateAwbDtoException;
import ro.uaic.info.parcel.gatewayapp.api.DeliveryApi;
import ro.uaic.info.parcel.gatewayapp.client.DeliveryServiceClient;
import ro.uaic.info.parcel.gatewayapp.domain.exception.MissingJwtCookieException;
import ro.uaic.info.parcel.gatewayapp.util.CookieExtractor;

@Controller
public class DeliveryController implements DeliveryApi {
    @Autowired
    private DeliveryServiceClient deliveryServiceClient;

    @Override
    public ResponseEntity<DeliveryRoute> getTodayRoute(HttpServletRequest request) throws MissingJwtCookieException {
        String jwtToken = CookieExtractor.extractJwtTokenFromCookie(request);

        return deliveryServiceClient.getTodayRoute(jwtToken);
    }

    @Override
    public ResponseEntity<String> updateAwbStatus(String awbNumber, UpdateAwbStatusDto dto, HttpServletRequest request) throws AwbDoesNotExistException, InvalidUpdateAwbDtoException, MissingJwtCookieException {
        String jwtToken = CookieExtractor.extractJwtTokenFromCookie(request);

        ResponseEntity<Awb> response = deliveryServiceClient.updateAwbStatus(jwtToken, awbNumber, dto);
        return ResponseEntity.status(response.getStatusCode()).body("");
    }
}
