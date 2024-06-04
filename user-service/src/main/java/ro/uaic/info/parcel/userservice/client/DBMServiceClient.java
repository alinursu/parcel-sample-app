package ro.uaic.info.parcel.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ro.uaic.info.parcel.database_management_service.domain.User;

@FeignClient(name = "dbmsFeignClient", url = "${client.dbmServiceBaseUrl}", configuration = DBMServiceClientConfiguration.class, dismiss404 = true)
public interface DBMServiceClient {
    @GetMapping(value = "/api/v1/user", consumes = "application/json")
    ResponseEntity<User> getUserByEmail(@RequestParam("email") String email);

    @PostMapping(value = "/api/v1/user", consumes = "application/json", produces = "application/json")
    ResponseEntity<User> addUser(User user);
}
