package ro.uaic.info.parcel.paymentservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ro.uaic.info.parcel.paymentservice.api.PaymentApi;
import ro.uaic.info.parcel.paymentservice.domain.exception.*;
import ro.uaic.info.parcel.paymentservice.service.PaymentService;

@Controller
public class PaymentController implements PaymentApi {
    @Autowired
    private PaymentService paymentService;

    @Override
    public ResponseEntity<String> payAwbOrder(String awbNumber, HttpServletRequest request) throws AwbDoesNotExistException, AwbIsNotYoursException, AwbAlreadyPayedException {
        String jwtToken = request.getHeader("Authorization");
        paymentService.payAwb(awbNumber, jwtToken);

        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
