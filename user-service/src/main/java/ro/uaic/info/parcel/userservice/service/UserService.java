package ro.uaic.info.parcel.userservice.service;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ro.uaic.info.parcel.database_management_service.domain.User;
import ro.uaic.info.parcel.database_management_service.domain.UserRole;
import ro.uaic.info.parcel.userservice.client.DBMServiceClient;
import ro.uaic.info.parcel.userservice.domain.dto.DtoValidator;
import ro.uaic.info.parcel.userservice.domain.dto.UserLoginDto;
import ro.uaic.info.parcel.userservice.domain.dto.UserRegisterDto;
import ro.uaic.info.parcel.userservice.domain.exception.*;
import ro.uaic.info.parcel.userservice.jwt.JwtTokenContent;
import ro.uaic.info.parcel.userservice.jwt.JwtTokenFields;
import ro.uaic.info.parcel.userservice.jwt.JwtTokenUtils;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private DBMServiceClient dbmServiceClient;

    public boolean registerUser(UserRegisterDto dto) throws EmailAlreadyInUseException, InvalidEmailAddressOnRegisterException, InvalidRegisterDtoException {
        DtoValidator.isValid(dto);

        ResponseEntity<User> response = dbmServiceClient.getUserByEmail(dto.getEmail());

        if (response.getStatusCode().value() != HttpStatus.NOT_FOUND.value()) {
            throw new EmailAlreadyInUseException(dto.getEmail());
        }

        User entity = User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();

        dbmServiceClient.addUser(entity);
        return true;
    }

    public String loginUser(UserLoginDto dto) throws IncorrectCredentialsException, InvalidLoginDtoException, InvalidEmailAddressOnLoginException {
        DtoValidator.isValid(dto);

        ResponseEntity<User> response = dbmServiceClient.getUserByEmail(dto.getEmail());

        if (response.getStatusCode().value() == HttpStatus.NOT_FOUND.value() || response.getBody() == null || !response.getBody().getPassword().equals(dto.getPassword())) {
            throw new IncorrectCredentialsException();
        }

        User entity = response.getBody();

        return new JwtTokenUtils().createToken(
                JwtTokenContent.builder()
                        .email(entity.getEmail())
                        .fullName(entity.getFirstName() + " " + entity.getLastName())
                        .build()
        );
    }

    public JwtTokenContent decodeJwtToken(String token) {
        Claims claims = new JwtTokenUtils().decodeToken(token);
        String email = (String) claims.getOrDefault(JwtTokenFields.EMAIL.getValue(), null);
        String fullName = (String) claims.getOrDefault(JwtTokenFields.FULL_NAME.getValue(), null);

        if (email == null || fullName == null) {
            return null;
        }

        return new JwtTokenContent(email, fullName);
    }

    public List<UserRole> getUserRoles(String email) throws IncorrectCredentialsException {
        ResponseEntity<User> response = dbmServiceClient.getUserByEmail(email);

        if (response.getStatusCode().value() == HttpStatus.NOT_FOUND.value() || response.getBody() == null) {
            throw new IncorrectCredentialsException();
        }

        return response.getBody().getRoles();
    }
}
