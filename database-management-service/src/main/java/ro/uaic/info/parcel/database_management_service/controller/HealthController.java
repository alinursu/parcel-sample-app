package ro.uaic.info.parcel.database_management_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ro.uaic.info.parcel.database_management_service.api.HealthApi;

@Controller
public class HealthController implements HealthApi {
    @Override
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Healthy");
    }
}
