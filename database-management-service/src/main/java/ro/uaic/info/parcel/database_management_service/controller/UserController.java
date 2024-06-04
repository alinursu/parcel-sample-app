package ro.uaic.info.parcel.database_management_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ro.uaic.info.parcel.database_management_service.api.UserApi;
import ro.uaic.info.parcel.database_management_service.domain.User;
import ro.uaic.info.parcel.database_management_service.domain.dto.AddUserRequestDto;
import ro.uaic.info.parcel.database_management_service.service.UserService;

@Controller
public class UserController implements UserApi {
    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<User> getUserByEmail(String email) {
        User user = userService.getUserByEmail(email);

        if (user != null) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @Override
    public ResponseEntity<User> addUser(AddUserRequestDto dto) {
        try {
            User user = User.builder()
                    .email(dto.getEmail())
                    .password(dto.getPassword())
                    .firstName(dto.getFirstName())
                    .lastName(dto.getLastName())
                    .build();

            user = userService.addUser(user);

            if (user != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(user);
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }
}
