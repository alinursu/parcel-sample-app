package ro.uaic.info.parcel.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ro.uaic.info.parcel.database_management_service.domain.UserRole;
import ro.uaic.info.parcel.userservice.api.UserApi;
import ro.uaic.info.parcel.userservice.domain.dto.UserLoginDto;
import ro.uaic.info.parcel.userservice.domain.dto.UserLoginResponseDto;
import ro.uaic.info.parcel.userservice.domain.dto.UserRegisterDto;
import ro.uaic.info.parcel.userservice.domain.exception.*;
import ro.uaic.info.parcel.userservice.jwt.JwtTokenContent;
import ro.uaic.info.parcel.userservice.jwt.JwtTokenUtils;
import ro.uaic.info.parcel.userservice.service.UserService;

import java.util.List;

@Controller
public class UserController implements UserApi {
    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<UserLoginResponseDto> loginUser(UserLoginDto dto) throws IncorrectCredentialsException, InvalidLoginDtoException, InvalidEmailAddressOnLoginException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UserLoginResponseDto(
                        JwtTokenUtils.getCookieName(),
                        userService.loginUser(dto)
                ));
    }

    @Override
    public ResponseEntity<String> registerUser(UserRegisterDto dto) throws EmailAlreadyInUseException, InvalidEmailAddressOnRegisterException, InvalidRegisterDtoException {
        userService.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @Override
    public ResponseEntity<JwtTokenContent> decodeJwtToken(String token) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.decodeJwtToken(token));
    }

    @Override
    public ResponseEntity<List<UserRole>> getUserRoles(String email) throws IncorrectCredentialsException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUserRoles(email));
    }
}
