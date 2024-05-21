package ro.uaic.info.parcelexampleapp.controller;

import ro.uaic.info.parcelexampleapp.api.PaymentApi;
import ro.uaic.info.parcelexampleapp.domain.Awb;
import ro.uaic.info.parcelexampleapp.domain.User;
import ro.uaic.info.parcelexampleapp.domain.exception.*;
import ro.uaic.info.parcelexampleapp.security.jwt.JwtTokenUtils;
import ro.uaic.info.parcelexampleapp.service.AwbService;
import ro.uaic.info.parcelexampleapp.service.PaymentService;
import ro.uaic.info.parcelexampleapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PaymentController implements PaymentApi {
    @Autowired
    private UserService userService;

    @Autowired
    private AwbService awbService;

    @Autowired
    private PaymentService paymentService;

    @Override
    public String payAwbOrder(String awbNumber, HttpServletRequest request) throws AwbDoesNotExistException, InvalidJwtCookieException, MissingJwtCookieException, AwbIsNotYoursException, AwbAlreadyPayedException {
        Awb awb = awbService.getAwbByNumber(awbNumber);

        User user = userService.getUserByEmail(
                new JwtTokenUtils().getEmailFromJwtCookie(request)
        );

        if (!awb.getOwner().equals(user)) {
            throw new AwbIsNotYoursException(awbNumber);
        }

        awb = paymentService.payAwb(awb);
        awbService.updateAwb(awb);

        return "redirect:/awb/" + awbNumber;
    }
}
