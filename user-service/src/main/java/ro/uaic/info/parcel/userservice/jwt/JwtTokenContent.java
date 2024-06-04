package ro.uaic.info.parcel.userservice.jwt;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JwtTokenContent {
    private String email;

    private String fullName;

    public Map<String, Object> getTokenClaims() {
        Map<String, Object> map = new HashMap<>();
        map.put(JwtTokenFields.EMAIL.getValue(), email);
        map.put(JwtTokenFields.FULL_NAME.getValue(), fullName);
        return map;
    }
}
