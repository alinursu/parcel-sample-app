package ro.uaic.info.parcelexampleapp.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ro.uaic.info.parcelexampleapp.domain.exception.*;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(value={InvalidLoginDtoException.class, IncorrectCredentialsException.class, InvalidEmailAddressOnLoginException.class})
    protected String handleLoginException(Exception exception, HttpServletRequest request, Model model) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "user/login-page";
    }

    @ExceptionHandler(value={InvalidEmailAddressOnRegisterException.class, InvalidRegisterDtoException.class, EmailAlreadyInUseException.class})
    protected String handleRegisterException(Exception exception, HttpServletRequest request, Model model) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "user/register-page";
    }

    @ExceptionHandler(value={InvalidAwbDtoException.class})
    protected String handleInvalidAwbDtoException(Exception exception, HttpServletRequest request, Model model) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "awb/create-awb-page";
    }

    @ExceptionHandler(value={InvalidUpdateAwbDtoException.class})
    protected ResponseEntity<String> handleInvalidUpdateAwbDtoException(Exception exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(value={Exception.class})
    protected String handleGenericException(Exception exception, HttpServletRequest request, Model model) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "common/error-page";
    }
}
