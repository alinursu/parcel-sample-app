package ro.uaic.info.parcel.gatewayapp.api;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.uaic.info.parcel.gatewayapp.domain.exception.InternalServerErrorException;
import ro.uaic.info.parcel.userservice.domain.dto.UserLoginDto;
import ro.uaic.info.parcel.userservice.domain.dto.UserRegisterDto;
import ro.uaic.info.parcel.userservice.domain.exception.*;

@RequestMapping("/user")
public interface UserApi {
    @GetMapping("/login")
    String loginPage(Model model);

    @GetMapping("/register")
    String registerPage(Model model);

    @PostMapping("/login")
    String loginUser(UserLoginDto dto, HttpServletResponse response) throws IncorrectCredentialsException, InvalidLoginDtoException, InvalidEmailAddressOnLoginException, InternalServerErrorException;

    @PostMapping("/register")
    String registerUser(UserRegisterDto dto) throws EmailAlreadyInUseException, InvalidEmailAddressOnRegisterException, InvalidRegisterDtoException, InternalServerErrorException;

    @GetMapping("/logout")
    String logoutUser(HttpServletResponse response);
}
