package ro.uaic.info.parcel.deliveryservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.parcel.database_management_service.domain.Awb;
import ro.uaic.info.parcel.database_management_service.domain.DeliveryRoute;
import ro.uaic.info.parcel.database_management_service.domain.User;
import ro.uaic.info.parcel.database_management_service.domain.dto.AddAwbRequestDto;

import java.util.List;

@FeignClient(name = "dbmsFeignClient", url = "${client.dbmServiceBaseUrl}", configuration = ServiceClientConfiguration.class, dismiss404 = true)
public interface DBMServiceClient {
    @GetMapping(value = "/api/v1/user", consumes = "application/json")
    ResponseEntity<User> getUserByEmail(@RequestParam("email") String email);

    @GetMapping(value = "/api/v1/awb", consumes = "application/json")
    ResponseEntity<List<Awb>> getAllAwbs();

    @GetMapping(value = "/api/v1/delivery", consumes = "application/json")
    ResponseEntity<List<DeliveryRoute>> getAllDeliveryRoutes();

    @GetMapping(value = "/api/v1/delivery/{id}", consumes = "application/json")
    ResponseEntity<List<DeliveryRoute>> getAllDeliveryRoutesOfUser(@PathVariable("id") Long id);

    @GetMapping(value = "/api/v1/awb/{number}", consumes = "application/json")
    ResponseEntity<Awb> getAwbByUniqueNumber(@PathVariable("number") String number);

    @PostMapping(value = "/api/v1/awb", consumes = "application/json", produces = "application/json")
    ResponseEntity<Awb> addAwb(AddAwbRequestDto awb);

    @GetMapping(value = "/api/v1/delivery", consumes = "application/json")
    ResponseEntity<DeliveryRoute> addDeliveryRoute(DeliveryRoute deliveryRoute);

    @PutMapping(value = "/api/v1/awb", consumes = "application/json", produces = "application/json")
    ResponseEntity<Awb> updateAwb(Awb awb);
}
