package ro.uaic.info.parcel.gatewayapp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.parcel.database_management_service.domain.Awb;
import ro.uaic.info.parcel.database_management_service.domain.DeliveryRoute;
import ro.uaic.info.parcel.deliveryservice.client.ServiceClientConfiguration;
import ro.uaic.info.parcel.deliveryservice.domain.dto.CreateAwbDto;
import ro.uaic.info.parcel.deliveryservice.domain.dto.UpdateAwbStatusDto;

import java.util.List;

@FeignClient(name = "deliveryFeignClient", url = "${client.deliveryServiceBaseUrl}", configuration = ServiceClientConfiguration.class, dismiss404 = true)
public interface DeliveryServiceClient {
    @GetMapping(value = "/api/v1/awb", consumes = "application/json")
    ResponseEntity<List<Awb>> getAwbsOfUser(@RequestHeader("Authorization") String jwtToken);

    @GetMapping(value = "/api/v1/awb/{uniqueNumber}", consumes = "application/json")
    ResponseEntity<Awb> getAwbOfUserByUniqueNumber(@RequestHeader("Authorization") String jwtToken, @PathVariable("uniqueNumber") String uniqueNumber);

    @GetMapping(value = "/api/v1/awb/nonDelivered", consumes = "application/json")
    ResponseEntity<List<Awb>> getAllNonDeliveredAwbs();

    @GetMapping(value = "/api/v1/delivery", consumes = "application/json")
    ResponseEntity<DeliveryRoute> getTodayRoute(@RequestHeader("Authorization") String jwtToken);

    @GetMapping(value = "/api/v1/delivery/all", consumes = "application/json")
    ResponseEntity<List<DeliveryRoute>> getAllRoutesForToday();

    @PostMapping(value = "/api/v1/delivery", consumes = "application/json", produces = "application/json")
    ResponseEntity<Awb> createAwb(@RequestHeader("Authorization") String jwtToken, CreateAwbDto dto);

    @PutMapping(value = "/api/v1/delivery/awb/{awbNumber}", consumes = "application/json", produces = "application/json")
    ResponseEntity<Awb> updateAwbStatus(@RequestHeader("Authorization") String jwtToken, @PathVariable("awbNumber") String awbNumber, UpdateAwbStatusDto dto);
}
