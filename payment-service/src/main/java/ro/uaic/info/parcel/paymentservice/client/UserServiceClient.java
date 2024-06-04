package ro.uaic.info.parcel.paymentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ro.uaic.info.parcel.userservice.jwt.JwtTokenContent;

@FeignClient(name = "userFeignClient", url = "${client.userServiceBaseUrl}", configuration = ServiceClientConfiguration.class, dismiss404 = true)
public interface UserServiceClient {
    @GetMapping(value = "/api/v1/user/decode", consumes = "application/json")
    ResponseEntity<JwtTokenContent> decodeJwtToken(@RequestParam("token") String token);
}
