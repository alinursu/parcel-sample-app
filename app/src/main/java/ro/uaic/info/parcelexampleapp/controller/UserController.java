package ro.uaic.info.parcelexampleapp.controller;

import ro.uaic.info.parcelexampleapp.api.UserApi;
import ro.uaic.info.parcelexampleapp.domain.dto.UserLoginDto;
import ro.uaic.info.parcelexampleapp.domain.dto.UserRegisterDto;
import ro.uaic.info.parcelexampleapp.domain.exception.EmailAlreadyInUseException;
import ro.uaic.info.parcelexampleapp.domain.exception.IncorrectCredentialsException;
import ro.uaic.info.parcelexampleapp.domain.exception.InvalidDtoException;
import ro.uaic.info.parcelexampleapp.security.jwt.JwtTokenUtils;
import ro.uaic.info.parcelexampleapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController implements UserApi {
    @Autowired
    private UserService userService;

    @Override
    public String loginPage() {
        return "user/login-page";
    }

    @Override
    public String registerPage() {
        return "user/register-page";
    }

    @Override
    public String loginUser(UserLoginDto dto, HttpServletResponse response) throws IncorrectCredentialsException, InvalidDtoException {
        String jwtToken = userService.loginUser(dto);

        Cookie cookie = new Cookie(JwtTokenUtils.getCookieName(), jwtToken);
        cookie.setMaxAge(3600);

        response.addCookie(cookie);

        return "redirect:/";
    }

    @Override
    public String registerUser(UserRegisterDto dto) throws EmailAlreadyInUseException, InvalidDtoException {
        userService.registerUser(dto);
        return "redirect:/";
    }

    @Override
    public String logoutUser(HttpServletResponse response) {
        Cookie cookie = new Cookie(JwtTokenUtils.getCookieName(), null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);

        return "redirect:/";
    }
}
