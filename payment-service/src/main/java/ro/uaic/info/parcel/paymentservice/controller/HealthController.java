package ro.uaic.info.parcel.paymentservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ro.uaic.info.parcel.paymentservice.api.HealthApi;

@Controller
public class HealthController implements HealthApi {
    @Override
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Healthy");
    }
}
