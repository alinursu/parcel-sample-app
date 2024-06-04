package ro.uaic.info.parcel.gatewayapp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import ro.uaic.info.parcel.database_management_service.domain.UserRole;
import ro.uaic.info.parcel.gatewayapp.client.UserServiceClient;
import ro.uaic.info.parcel.userservice.jwt.JwtTokenContent;
import ro.uaic.info.parcel.userservice.jwt.JwtTokenUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class RequestAuthenticationFilter extends OncePerRequestFilter {
    private UserServiceClient userServiceClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Cookie cookie = getJwtCookieFromRequest(request);

            if (cookie != null) {
                ResponseEntity<JwtTokenContent> jwtResponse = userServiceClient.decodeJwtToken(cookie.getValue());
                if (jwtResponse.getStatusCode().value() != HttpStatus.OK.value() || jwtResponse.getBody() == null) {
                    throw new Exception("Could not decode JWT token from cookie");
                }

                JwtTokenContent body = jwtResponse.getBody();
                ResponseEntity<List<UserRole>> rolesResponse = userServiceClient.getUserRoles(body.getEmail());
                if (rolesResponse.getStatusCode().value() != HttpStatus.OK.value() || rolesResponse.getBody() == null) {
                    throw new Exception("Could not get user roles");
                }

                List<UserRole> userRoles = rolesResponse.getBody();

                List<AuthenticatedUserAuthority> authorities = userRoles.stream()
                        .map(r -> new AuthenticatedUserAuthority(r.getRole()))
                        .toList();

                AuthenticatedUser authenticatedUser = new AuthenticatedUser(body.getEmail().strip(), authorities);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        authenticatedUser, null, authenticatedUser.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private Cookie getJwtCookieFromRequest(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }

        Optional<Cookie> cookie = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(JwtTokenUtils.getCookieName()))
                .findFirst();

        return cookie.orElse(null);
    }
}
