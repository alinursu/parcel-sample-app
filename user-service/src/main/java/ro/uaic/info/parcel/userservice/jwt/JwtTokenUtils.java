package ro.uaic.info.parcel.userservice.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtTokenUtils {
    private static final MacAlgorithm SIGNATURE_ALGORITHM = Jwts.SIG.HS256;
    private static final String SIGNATURE_ALGORITHM_NAME = "HmacSHA256";
    private static final String KEY = "12345678901234567890123456789012345679012345678901234567890";
    private static final String ISSUER = "app-name";
    private static final String COOKIE_NAME = "jwt";

    public static String getCookieName() {
        return COOKIE_NAME;
    }

    private Clock clock;

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
        SecretKeySpec signingKey = new SecretKeySpec(apiKeySecretBytes, SIGNATURE_ALGORITHM_NAME);

        return Jwts.builder()
                .id(null)
                .issuer(ISSUER)
                .issuedAt(now)
                .claims(jwtTokenContent.getTokenClaims())
                .signWith(signingKey, SIGNATURE_ALGORITHM)
                .compact();
    }

    public Claims decodeToken(String jwtToken) {
        return Jwts.parser()
                .verifyWith(new SecretKeySpec(DatatypeConverter.parseBase64Binary(KEY), SIGNATURE_ALGORITHM_NAME))
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }
}