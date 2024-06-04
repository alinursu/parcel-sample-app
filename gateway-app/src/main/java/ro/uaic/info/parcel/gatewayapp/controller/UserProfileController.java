package ro.uaic.info.parcel.gatewayapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.uaic.info.parcel.gatewayapp.api.UserProfileApi;
import ro.uaic.info.parcel.gatewayapp.client.UserServiceClient;
import ro.uaic.info.parcel.gatewayapp.domain.exception.InternalServerErrorException;
import ro.uaic.info.parcel.gatewayapp.domain.exception.MissingJwtCookieException;
import ro.uaic.info.parcel.gatewayapp.util.CookieExtractor;
import ro.uaic.info.parcel.userservice.jwt.JwtTokenContent;

@Controller
public class UserProfileController implements UserProfileApi {
    @Autowired
    private UserServiceClient userServiceClient;

    @Override
    public String userProfilePage(Model model, HttpServletRequest request) throws InternalServerErrorException, MissingJwtCookieException {
        String jwtToken = CookieExtractor.extractJwtTokenFromCookie(request);
        ResponseEntity<JwtTokenContent> response = userServiceClient.decodeJwtToken(jwtToken);
        if (response.getStatusCode().value() != HttpStatus.OK.value() || response.getBody() == null) {
            throw new InternalServerErrorException("An error occurred");
        }

        model.addAttribute("emailAddress", response.getBody().getEmail());
        model.addAttribute("fullName", response.getBody().getFullName());

        return "user/user-profile-page";
    }
}
