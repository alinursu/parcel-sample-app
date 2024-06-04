package ro.uaic.info.parcel.gatewayapp.controller.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ro.uaic.info.parcel.gatewayapp.security.AuthenticatedUser;
import ro.uaic.info.parcel.gatewayapp.security.AuthenticatedUserAuthority;

import java.util.Optional;

@ControllerAdvice
public class CommonDataControllerAdvice {
    @Autowired
    private HttpServletRequest request;

    @ModelAttribute("loggedIn")
    public Boolean getLoggedIn(HttpServletRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return false;
            }

            AuthenticatedUser principal = (AuthenticatedUser) authentication.getPrincipal();
            if (principal == null) {
                return false;
            }

            return principal.getUsername() != null && principal.getUsername().strip().length() > 0;
        } catch (Exception ignored) {
            return false;
        }
    }

    @ModelAttribute("isAdmin")
    public Boolean getIsAdmin(HttpServletRequest request) {
        try {
            if (!getLoggedIn(request)) {
                return false;
            }

            AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Optional<AuthenticatedUserAuthority> authority = authenticatedUser.getAuthorities().stream()
                    .filter(a -> a.getAuthority().equals("Admin"))
                    .findFirst();
            return authority.isPresent();
        } catch (Exception ignored) {
            return false;
        }
    }
}
