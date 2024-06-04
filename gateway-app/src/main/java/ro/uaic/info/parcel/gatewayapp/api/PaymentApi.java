package ro.uaic.info.parcel.gatewayapp.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.uaic.info.parcel.gatewayapp.domain.exception.InternalServerErrorException;
import ro.uaic.info.parcel.gatewayapp.domain.exception.MissingJwtCookieException;
import ro.uaic.info.parcel.paymentservice.domain.exception.AwbAlreadyPayedException;
import ro.uaic.info.parcel.paymentservice.domain.exception.AwbDoesNotExistException;
import ro.uaic.info.parcel.paymentservice.domain.exception.AwbIsNotYoursException;

@RequestMapping("/payment")
public interface PaymentApi {
    @PostMapping("/{awbNumber}")
    String payAwbOrder(@PathVariable String awbNumber, HttpServletRequest request) throws AwbDoesNotExistException, AwbIsNotYoursException, AwbAlreadyPayedException, MissingJwtCookieException, InternalServerErrorException;
}
