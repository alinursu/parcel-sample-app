package ro.uaic.info.parcelexampleapp.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import ro.uaic.info.parcelexampleapp.domain.User;
import ro.uaic.info.parcelexampleapp.domain.exception.InvalidJwtCookieException;
import ro.uaic.info.parcelexampleapp.domain.exception.MissingJwtCookieException;
import ro.uaic.info.parcelexampleapp.security.jwt.JwtTokenUtils;
import ro.uaic.info.parcelexampleapp.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class RequestAuthenticationFilter extends OncePerRequestFilter {

    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String emailAddress = new JwtTokenUtils().getEmailFromJwtCookie(request);

            if (emailAddress == null || emailAddress.strip().length() == 0) {
                throw new Exception("Invalid email address in JWT Cookie.");
            }

            User user = userService.getUserByEmail(emailAddress);
            if (user == null) {
                throw new Exception("Invalid JWT token.");
            }

            List<AuthenticatedUserAuthority> authorities = user.getRoles().stream()
                    .map(r -> new AuthenticatedUserAuthority(r.getRole()))
                    .collect(Collectors.toList());

            AuthenticatedUser authenticatedUser = new AuthenticatedUser(emailAddress.strip(), authorities);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authenticatedUser, null, authenticatedUser.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception ignored) {
//            System.out.println("Error: " + e.getMessage());
//            ignored.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}
