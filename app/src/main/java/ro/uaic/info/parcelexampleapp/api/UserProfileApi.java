package ro.uaic.info.parcelexampleapp.api;

import ro.uaic.info.parcelexampleapp.domain.exception.InternalServerErrorException;
import ro.uaic.info.parcelexampleapp.domain.exception.InvalidJwtCookieException;
import ro.uaic.info.parcelexampleapp.domain.exception.MissingJwtCookieException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/user/profile")
public interface UserProfileApi {
    @GetMapping
    String userProfilePage(HttpServletRequest request) throws InvalidJwtCookieException, MissingJwtCookieException, InternalServerErrorException;
}
