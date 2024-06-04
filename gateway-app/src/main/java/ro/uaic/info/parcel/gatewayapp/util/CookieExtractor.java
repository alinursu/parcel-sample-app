package ro.uaic.info.parcel.gatewayapp.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import ro.uaic.info.parcel.gatewayapp.domain.exception.MissingJwtCookieException;
import ro.uaic.info.parcel.userservice.jwt.JwtTokenUtils;

import java.util.Arrays;
import java.util.Optional;

public class CookieExtractor {
    public static String extractJwtTokenFromCookie(HttpServletRequest request) throws MissingJwtCookieException {
        if (request.getCookies() == null) {
            throw new MissingJwtCookieException();
        }

        Optional<Cookie> cookie = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(JwtTokenUtils.getCookieName()))
                .findFirst();

        if (cookie.isEmpty()) {
            throw new MissingJwtCookieException();
        }

        return cookie.get().getValue();
    }
}
