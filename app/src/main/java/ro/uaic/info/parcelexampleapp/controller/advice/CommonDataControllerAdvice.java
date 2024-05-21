package ro.uaic.info.parcelexampleapp.controller.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ro.uaic.info.parcelexampleapp.domain.User;
import ro.uaic.info.parcelexampleapp.domain.UserRole;
import ro.uaic.info.parcelexampleapp.security.AuthenticatedUser;
import ro.uaic.info.parcelexampleapp.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@ControllerAdvice
public class CommonDataControllerAdvice {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserService userService;

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
//            ignored.printStackTrace();
            return false;
        }
    }

    @ModelAttribute("isAdmin")
    public Boolean getIsAdmin(HttpServletRequest request) {
        try {
            if (!getLoggedIn(request)) {
                return false;
            }

            String userEmail = ((AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                    .getUsername();

            User user = userService.getUserByEmail(userEmail);
            if (user == null) {
                return false;
            }

            Optional<UserRole> role = user.getRoles().stream()
                    .filter(r -> r.getRole().equals("Admin"))
                    .findFirst();

            return role.isPresent();
        } catch (Exception ignored) {
//            ignored.printStackTrace();
            return false;
        }
    }
}
