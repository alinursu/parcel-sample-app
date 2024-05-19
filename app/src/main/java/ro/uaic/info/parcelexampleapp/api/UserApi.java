package ro.uaic.info.parcelexampleapp.api;

import ro.uaic.info.parcelexampleapp.domain.dto.UserLoginDto;
import ro.uaic.info.parcelexampleapp.domain.dto.UserRegisterDto;
import ro.uaic.info.parcelexampleapp.domain.exception.EmailAlreadyInUseException;
import ro.uaic.info.parcelexampleapp.domain.exception.IncorrectCredentialsException;
import ro.uaic.info.parcelexampleapp.domain.exception.InvalidDtoException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("/user")
public interface UserApi {
    @GetMapping("/login")
    String loginPage();

    @GetMapping("/register")
    String registerPage();

    @PostMapping("/login")
    String loginUser(UserLoginDto dto, HttpServletResponse response) throws IncorrectCredentialsException, InvalidDtoException;

    @PostMapping("/register")
    String registerUser(UserRegisterDto dto) throws EmailAlreadyInUseException, InvalidDtoException;

    @PostMapping("/logout")
    String logoutUser(HttpServletResponse response);
}
