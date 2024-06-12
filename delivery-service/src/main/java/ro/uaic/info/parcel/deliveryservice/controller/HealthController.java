package ro.uaic.info.parcel.deliveryservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ro.uaic.info.parcel.deliveryservice.api.HealthApi;

@Controller
public class HealthController implements HealthApi {
    @Override
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Healthy");
    }
}
