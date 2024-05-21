package ro.uaic.info.parcelexampleapp.controller;

import org.springframework.ui.Model;
import ro.uaic.info.parcelexampleapp.api.UserApi;
import ro.uaic.info.parcelexampleapp.domain.dto.UserLoginDto;
import ro.uaic.info.parcelexampleapp.domain.dto.UserRegisterDto;
import ro.uaic.info.parcelexampleapp.domain.exception.*;
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
    public String loginPage(Model model) {
        model.addAttribute("loginRequestDto", new UserLoginDto());
        return "user/login-page";
    }

    @Override
    public String registerPage(Model model) {
        model.addAttribute("registerRequestDto", new UserRegisterDto());
        return "user/register-page";
    }

    @Override
    public String loginUser(UserLoginDto dto, HttpServletResponse response) throws IncorrectCredentialsException, InvalidLoginDtoException, InvalidEmailAddressOnLoginException {
        String jwtToken = userService.loginUser(dto);

        Cookie cookie = new Cookie(JwtTokenUtils.getCookieName(), jwtToken);
        cookie.setMaxAge(3600);
        cookie.setPath("/");

        response.addCookie(cookie);

        return "redirect:/";
    }

    @Override
    public String registerUser(UserRegisterDto dto) throws EmailAlreadyInUseException, InvalidEmailAddressOnRegisterException, InvalidRegisterDtoException {
        userService.registerUser(dto);
        return "redirect:/user/login";
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
