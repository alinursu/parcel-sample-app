package ro.uaic.info.parcel.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ro.uaic.info.parcel.database_management_service.domain.dto.ExceptionResponseDto;
import ro.uaic.info.parcel.userservice.domain.exception.*;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(value = {InvalidLoginDtoException.class, InvalidEmailAddressOnLoginException.class})
    protected ResponseEntity<ExceptionResponseDto> handleLoginDtoExceptions(InvalidLoginDtoException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(value = {IncorrectCredentialsException.class})
    protected ResponseEntity<ExceptionResponseDto> handleIncorrectCredentialsException(IncorrectCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(value = {InvalidRegisterDtoException.class, InvalidEmailAddressOnRegisterException.class})
    protected ResponseEntity<ExceptionResponseDto> handleRegisterDtoExceptions(InvalidLoginDtoException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(value = {EmailAlreadyInUseException.class})
    protected ResponseEntity<ExceptionResponseDto> handleEmailAlreadyInUseException(EmailAlreadyInUseException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ExceptionResponseDto> handleGenericException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponseDto(exception.getMessage()));
    }
}
