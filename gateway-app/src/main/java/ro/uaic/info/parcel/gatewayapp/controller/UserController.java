package ro.uaic.info.parcel.gatewayapp.controller;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ro.uaic.info.parcel.gatewayapp.api.UserApi;
import jakarta.servlet.http.HttpServletResponse;
import ro.uaic.info.parcel.gatewayapp.client.UserServiceClient;
import ro.uaic.info.parcel.gatewayapp.domain.exception.InternalServerErrorException;
import ro.uaic.info.parcel.userservice.domain.dto.UserLoginDto;
import ro.uaic.info.parcel.userservice.domain.dto.UserLoginResponseDto;
import ro.uaic.info.parcel.userservice.domain.dto.UserRegisterDto;
import ro.uaic.info.parcel.userservice.domain.exception.*;
import ro.uaic.info.parcel.userservice.jwt.JwtTokenUtils;

@Controller
public class UserController implements UserApi {
    @Autowired
    private UserServiceClient userServiceClient;

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
    public String loginUser(UserLoginDto dto, HttpServletResponse response) throws IncorrectCredentialsException, InvalidLoginDtoException, InvalidEmailAddressOnLoginException, InternalServerErrorException {
        ResponseEntity<UserLoginResponseDto> resp = userServiceClient.loginUser(dto);
        if (resp.getStatusCode().value() != HttpStatus.OK.value() || resp.getBody() == null) {
            throw new InternalServerErrorException("An error occurred.");
        }

        String cookieName = resp.getBody().getCookieName();
        String jwtToken = resp.getBody().getToken();

        Cookie cookie = new Cookie(cookieName, jwtToken);
        cookie.setMaxAge(3600);
        cookie.setPath("/");

        response.addCookie(cookie);

        return "redirect:/";
    }

    @Override
    public String registerUser(UserRegisterDto dto) throws EmailAlreadyInUseException, InvalidEmailAddressOnRegisterException, InvalidRegisterDtoException, InternalServerErrorException {
        ResponseEntity<String> resp = userServiceClient.registerUser(dto);
        if (resp.getStatusCode().value() != HttpStatus.OK.value() || resp.getBody() == null) {
            throw new InternalServerErrorException("An error occurred.");
        }

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
