package ro.uaic.info.parcel.deliveryservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ro.uaic.info.parcel.database_management_service.domain.dto.ExceptionResponseDto;
import ro.uaic.info.parcel.deliveryservice.domain.exception.AwbDoesNotExistException;
import ro.uaic.info.parcel.deliveryservice.domain.exception.InvalidAwbDtoException;
import ro.uaic.info.parcel.deliveryservice.domain.exception.InvalidUpdateAwbDtoException;
import ro.uaic.info.parcel.deliveryservice.domain.exception.UserNotLoggedInException;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(value = {AwbDoesNotExistException.class})
    protected ResponseEntity<ExceptionResponseDto> handleAwbDoesNotExistException(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(value = {UserNotLoggedInException.class})
    protected ResponseEntity<ExceptionResponseDto> handleUserNotLoggedInException(Exception exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(value = {InvalidAwbDtoException.class, InvalidUpdateAwbDtoException.class})
    protected ResponseEntity<ExceptionResponseDto> handleInvalidDtoExceptions(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ExceptionResponseDto> handleGenericException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponseDto(exception.getMessage()));
    }
}
