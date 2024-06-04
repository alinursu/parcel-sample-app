package ro.uaic.info.parcel.paymentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ro.uaic.info.parcel.database_management_service.domain.Awb;
import ro.uaic.info.parcel.database_management_service.domain.User;

@FeignClient(name = "dbmsFeignClient", url = "${client.dbmServiceBaseUrl}", configuration = ServiceClientConfiguration.class, dismiss404 = true)
public interface DBMServiceClient {
    @GetMapping(value = "/api/v1/user", consumes = "application/json")
    ResponseEntity<User> getUserByEmail(@RequestParam("email") String email);

    @GetMapping(value = "/api/v1/awb/{number}", consumes = "application/json")
    ResponseEntity<Awb> getAwbByUniqueNumber(@PathVariable("number") String number);

    @PutMapping(value = "/api/v1/awb", consumes = "application/json", produces = "application/json")
    ResponseEntity<Awb> updateAwb(Awb awb);
}
