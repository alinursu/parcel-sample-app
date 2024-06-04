package ro.uaic.info.parcel.gatewayapp.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.uaic.info.parcel.gatewayapp.domain.exception.InternalServerErrorException;
import ro.uaic.info.parcel.gatewayapp.domain.exception.MissingJwtCookieException;

@RequestMapping("/user/profile")
public interface UserProfileApi {
    @GetMapping
    String userProfilePage(Model model, HttpServletRequest request) throws InternalServerErrorException, MissingJwtCookieException;
}
