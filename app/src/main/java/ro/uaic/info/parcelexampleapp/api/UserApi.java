package ro.uaic.info.parcelexampleapp.api;

import org.springframework.ui.Model;
import ro.uaic.info.parcelexampleapp.domain.dto.UserLoginDto;
import ro.uaic.info.parcelexampleapp.domain.dto.UserRegisterDto;
import ro.uaic.info.parcelexampleapp.domain.exception.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("/user")
public interface UserApi {
    @GetMapping("/login")
    String loginPage(Model model);

    @GetMapping("/register")
    String registerPage(Model model);

    @PostMapping("/login")
    String loginUser(UserLoginDto dto, HttpServletResponse response) throws IncorrectCredentialsException, InvalidLoginDtoException, InvalidEmailAddressOnLoginException;

    @PostMapping("/register")
    String registerUser(UserRegisterDto dto) throws EmailAlreadyInUseException, InvalidEmailAddressOnRegisterException, InvalidRegisterDtoException;

    @GetMapping("/logout")
    String logoutUser(HttpServletResponse response);
}
