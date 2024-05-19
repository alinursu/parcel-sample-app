package ro.uaic.info.parcelexampleapp.controller;

import ro.uaic.info.parcelexampleapp.domain.exception.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = AwbAlreadyPayedException.class)
    public String handleAwbAlreadyPayedException(RuntimeException exception, WebRequest request) {
        return "redirect:/error/403";
    }

    @ExceptionHandler(value = AwbDoesNotExistException.class)
    public String handleAwbDoesNotExistException(RuntimeException exception, WebRequest request) {
        return "redirect:/error/404";
    }


    @ExceptionHandler(value = AwbIsNotYoursException.class)
    public String handleAwbIsNotYoursException(RuntimeException exception, WebRequest request) {
        return "redirect:/error/403";
    }


    @ExceptionHandler(value = EmailAlreadyInUseException.class)
    public String handleEmailAlreadyInUseException(RuntimeException exception, WebRequest request) {
        return "redirect:/error/409";
    }


    @ExceptionHandler(value = IncorrectCredentialsException.class)
    public String handleIncorrectCredentialsException(RuntimeException exception, WebRequest request) {
        return "redirect:/error/404";
    }


    @ExceptionHandler(value = InternalServerErrorException.class)
    public String handleInternalServerErrorException(RuntimeException exception, WebRequest request) {
        return "redirect:/error/500";
    }


    @ExceptionHandler(value = InvalidDtoException.class)
    public String handleInvalidDtoException(RuntimeException exception, WebRequest request) {
        return "redirect:/error/400";
    }


    @ExceptionHandler(value = InvalidJwtCookieException.class)
    public String handleInvalidJwtCookieException(RuntimeException exception, WebRequest request) {
        return "redirect:/error/403";
    }

    @ExceptionHandler(value = MissingJwtCookieException.class)
    public String handleMissingJwtCookieException(RuntimeException exception, WebRequest request) {
        return "redirect:/error/403";
    }
}
