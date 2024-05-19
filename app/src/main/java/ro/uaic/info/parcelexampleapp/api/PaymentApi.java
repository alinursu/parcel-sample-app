package ro.uaic.info.parcelexampleapp.api;

import ro.uaic.info.parcelexampleapp.domain.exception.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/payment")
public interface PaymentApi {
    @PostMapping("/{awbNumber}")
    String payAwbOrder(@PathVariable String awbNumber, HttpServletRequest request) throws AwbDoesNotExistException, InvalidJwtCookieException, MissingJwtCookieException, AwbIsNotYoursException, AwbAlreadyPayedException;
}
