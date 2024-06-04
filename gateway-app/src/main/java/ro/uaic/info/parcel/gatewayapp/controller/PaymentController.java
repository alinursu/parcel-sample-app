package ro.uaic.info.parcel.gatewayapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ro.uaic.info.parcel.gatewayapp.api.PaymentApi;
import ro.uaic.info.parcel.gatewayapp.client.PaymentServiceClient;
import ro.uaic.info.parcel.gatewayapp.domain.exception.InternalServerErrorException;
import ro.uaic.info.parcel.gatewayapp.domain.exception.MissingJwtCookieException;
import ro.uaic.info.parcel.gatewayapp.util.CookieExtractor;
import ro.uaic.info.parcel.paymentservice.domain.exception.AwbAlreadyPayedException;
import ro.uaic.info.parcel.paymentservice.domain.exception.AwbDoesNotExistException;
import ro.uaic.info.parcel.paymentservice.domain.exception.AwbIsNotYoursException;

@Controller
public class PaymentController implements PaymentApi {
    @Autowired
    private PaymentServiceClient paymentServiceClient;

    @Override
    public String payAwbOrder(String awbNumber, HttpServletRequest request) throws AwbDoesNotExistException, AwbIsNotYoursException, AwbAlreadyPayedException, MissingJwtCookieException, InternalServerErrorException {
        String jwtToken = CookieExtractor.extractJwtTokenFromCookie(request);

        ResponseEntity<String> response = paymentServiceClient.payAwbOrder(jwtToken, awbNumber);
        if (response.getStatusCode().value() != HttpStatus.OK.value()) {
            throw new InternalServerErrorException("An error occured.");
        }

        return "redirect:/awb/" + awbNumber;
    }
}
