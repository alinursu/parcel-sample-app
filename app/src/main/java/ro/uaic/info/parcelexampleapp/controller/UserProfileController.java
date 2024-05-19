package ro.uaic.info.parcelexampleapp.controller;

import ro.uaic.info.parcelexampleapp.api.UserProfileApi;
import ro.uaic.info.parcelexampleapp.domain.User;
import ro.uaic.info.parcelexampleapp.domain.exception.InternalServerErrorException;
import ro.uaic.info.parcelexampleapp.domain.exception.InvalidJwtCookieException;
import ro.uaic.info.parcelexampleapp.domain.exception.MissingJwtCookieException;
import ro.uaic.info.parcelexampleapp.security.jwt.JwtTokenUtils;
import ro.uaic.info.parcelexampleapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserProfileController implements UserProfileApi {
    @Autowired
    private UserService userService;

    @Override
    public String userProfilePage(HttpServletRequest request) throws InvalidJwtCookieException, MissingJwtCookieException, InternalServerErrorException {
        String userEmail = new JwtTokenUtils().getEmailFromJwtCookie(request);

        User user = userService.getUserByEmail(userEmail);
        if (user == null) {
            throw new InternalServerErrorException();
        }

        // TODO: add user to model
        return "user/user-profile-page";
    }
}
