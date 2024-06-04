package ro.uaic.info.parcel.gatewayapp.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class AuthenticatedUserAuthority implements GrantedAuthority {
    private String role;

    @Override
    public String getAuthority() {
        return role;
    }
}
