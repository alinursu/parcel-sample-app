package ro.uaic.info.parcelexampleapp.security.jwt;

import ro.uaic.info.parcelexampleapp.domain.exception.InvalidJwtCookieException;
import ro.uaic.info.parcelexampleapp.domain.exception.MissingJwtCookieException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

public class JwtTokenUtils {
    private Clock clock;

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private static final String KEY = "12345678901234567890123456789012345679012345678901234567890";
    private static final String ISSUER = "app-name";

    private static final String COOKIE_NAME = "jwt";

    public static String getCookieName() {
        return COOKIE_NAME;
    }

    public JwtTokenUtils() {
        initDefaultClock();
    }

    public void initDefaultClock() {
        clock = Clock.system(
                Clock.systemDefaultZone().getZone()
        );
    }

    public String createToken(JwtTokenContent jwtTokenContent) {
        LocalDateTime localNow = LocalDateTime.now(clock);
        Date now = Date.from(localNow.atZone(ZoneId.systemDefault()).toInstant());

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(KEY);
        SecretKeySpec signingKey = new SecretKeySpec(apiKeySecretBytes, SIGNATURE_ALGORITHM.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setId(null)
                .setIssuedAt(now)
                .setIssuer(ISSUER)
                .setClaims(jwtTokenContent.getTokenClaims())
                .signWith(SIGNATURE_ALGORITHM, signingKey);

        return builder.compact();
    }

    public Claims decodeToken(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(KEY))
                .build()
                .parseClaimsJws(jwtToken).getBody();
    }

    public Claims getClaimsFromRequest(HttpServletRequest request) throws MissingJwtCookieException {
        if (request.getCookies() == null) {
            throw new MissingJwtCookieException();
        }

        Optional<Cookie> cookie = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(JwtTokenUtils.getCookieName()))
                .findFirst();

        if (cookie.isEmpty()) {
            throw new MissingJwtCookieException();
        }

        return decodeToken(cookie.get().getValue());
    }

    public String getEmailFromJwtCookie(HttpServletRequest request) throws MissingJwtCookieException, InvalidJwtCookieException {
        Claims claims = getClaimsFromRequest(request);

        try {
            return claims.get(JwtTokenFields.EMAIL.getValue()).toString();
        } catch (Exception e) {
            throw new InvalidJwtCookieException();
        }
    }
}