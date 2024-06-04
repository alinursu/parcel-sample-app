package ro.uaic.info.parcel.gatewayapp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.parcel.deliveryservice.client.ServiceClientConfiguration;

@FeignClient(name = "paymentFeignClient", url = "${client.paymentServiceBaseUrl}", configuration = ServiceClientConfiguration.class, dismiss404 = true)
public interface PaymentServiceClient {
    @PostMapping(value = "/api/v1/payment/{awbNumber}", consumes = "application/json", produces = "application/json")
    ResponseEntity<String> payAwbOrder(@RequestHeader("Authorization") String jwtToken, @PathVariable String awbNumber);
}
