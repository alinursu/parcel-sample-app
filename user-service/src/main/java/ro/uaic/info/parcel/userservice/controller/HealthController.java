package ro.uaic.info.parcel.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ro.uaic.info.parcel.userservice.api.HealthApi;

@Controller
public class HealthController implements HealthApi {
    @Override
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Healthy");
    }
}
