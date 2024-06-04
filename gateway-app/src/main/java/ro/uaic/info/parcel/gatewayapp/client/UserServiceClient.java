package ro.uaic.info.parcel.gatewayapp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.parcel.database_management_service.domain.UserRole;
import ro.uaic.info.parcel.deliveryservice.client.ServiceClientConfiguration;
import ro.uaic.info.parcel.userservice.domain.dto.UserLoginDto;
import ro.uaic.info.parcel.userservice.domain.dto.UserLoginResponseDto;
import ro.uaic.info.parcel.userservice.domain.dto.UserRegisterDto;
import ro.uaic.info.parcel.userservice.jwt.JwtTokenContent;

import java.util.List;

@FeignClient(name = "userFeignClient", url = "${client.userServiceBaseUrl}", configuration = ServiceClientConfiguration.class, dismiss404 = true)
public interface UserServiceClient {
    @PostMapping(value = "/api/v1/user/login", consumes = "application/json", produces = "application/json")
    ResponseEntity<UserLoginResponseDto> loginUser(UserLoginDto dto);

    @PostMapping(value = "/api/v1/user/register", consumes = "application/json", produces = "application/json")
    ResponseEntity<String> registerUser(@RequestBody UserRegisterDto dto);

    @GetMapping(value = "/api/v1/user/decode", consumes = "application/json")
    ResponseEntity<JwtTokenContent> decodeJwtToken(@RequestParam("token") String token);

    @GetMapping(value = "/api/v1/user/roles", consumes = "application/json")
    ResponseEntity<List<UserRole>> getUserRoles(@RequestParam("email") String email);
}
