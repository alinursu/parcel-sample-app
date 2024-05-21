package ro.uaic.info.parcelexampleapp.controller;

import ro.uaic.info.parcelexampleapp.api.DeliveryApi;
import ro.uaic.info.parcelexampleapp.domain.Awb;
import ro.uaic.info.parcelexampleapp.domain.DeliveryRoute;
import ro.uaic.info.parcelexampleapp.domain.User;
import ro.uaic.info.parcelexampleapp.domain.dto.DtoValidator;
import ro.uaic.info.parcelexampleapp.domain.dto.UpdateAwbStatusDto;
import ro.uaic.info.parcelexampleapp.domain.exception.*;
import ro.uaic.info.parcelexampleapp.security.jwt.JwtTokenUtils;
import ro.uaic.info.parcelexampleapp.service.AwbService;
import ro.uaic.info.parcelexampleapp.service.DeliveryService;
import ro.uaic.info.parcelexampleapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DeliveryController implements DeliveryApi {
    @Autowired
    private AwbService awbService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeliveryService deliveryService;

    @Override
    public ResponseEntity<DeliveryRoute> getTodayRoute(HttpServletRequest request) throws InvalidJwtCookieException, MissingJwtCookieException {
        User user = userService.getUserByEmail(
                new JwtTokenUtils().getEmailFromJwtCookie(request)
        );

        return ResponseEntity.ok(deliveryService.getDeliveryRouteForUser(user));
    }

    @Override
    public ResponseEntity<String> updateAwbStatus(String awbNumber, UpdateAwbStatusDto dto) throws AwbDoesNotExistException, InvalidUpdateAwbDtoException {
        DtoValidator.isValid(dto);

        Awb awb = awbService.getAwbByNumber(awbNumber);
        awb.setStatus(dto.getNewStatus());
        awbService.updateAwb(awb);

        return ResponseEntity.ok("Updated");
    }
}
