package ro.uaic.info.parcel.database_management_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ro.uaic.info.parcel.database_management_service.api.DeliveryRouteApi;
import ro.uaic.info.parcel.database_management_service.domain.DeliveryRoute;
import ro.uaic.info.parcel.database_management_service.service.DeliveryRouteService;

import java.util.List;

@Controller
public class DeliveryRouteController implements DeliveryRouteApi {
    @Autowired
    private DeliveryRouteService deliveryRouteService;

    @Override
    public ResponseEntity<List<DeliveryRoute>> getAllDeliveryRoutes() {
        return ResponseEntity.status(HttpStatus.OK).body(deliveryRouteService.getAllDeliveryRoutes());
    }

    @Override
    public ResponseEntity<List<DeliveryRoute>> getAllDeliveryRoutesOfUser(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(deliveryRouteService.getAllDeliveryRoutesOfUser(id));
    }

    @Override
    public ResponseEntity<DeliveryRoute> addDeliveryRoute(DeliveryRoute deliveryRoute) {
        return ResponseEntity.status(HttpStatus.CREATED).body(deliveryRouteService.addOrUpdate(deliveryRoute));
    }
}
