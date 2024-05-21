package ro.uaic.info.parcelexampleapp.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import ro.uaic.info.parcelexampleapp.api.UserProfileApi;
import ro.uaic.info.parcelexampleapp.domain.User;
import ro.uaic.info.parcelexampleapp.domain.exception.InternalServerErrorException;
import ro.uaic.info.parcelexampleapp.domain.exception.InvalidJwtCookieException;
import ro.uaic.info.parcelexampleapp.domain.exception.MissingJwtCookieException;
import ro.uaic.info.parcelexampleapp.security.AuthenticatedUser;
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
    public String userProfilePage(Model model, HttpServletRequest request) throws InternalServerErrorException {
        String userEmail = ((AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUsername();

        User user = userService.getUserByEmail(userEmail);
        if (user == null) {
            throw new InternalServerErrorException();
        }

        model.addAttribute("emailAddress", userEmail);
        model.addAttribute("fullName", user.getFirstName() + " " + user.getLastName());

        return "user/user-profile-page";
    }
}
