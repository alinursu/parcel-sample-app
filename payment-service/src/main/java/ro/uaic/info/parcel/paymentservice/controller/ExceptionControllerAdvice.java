package ro.uaic.info.parcel.paymentservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ro.uaic.info.parcel.database_management_service.domain.dto.ExceptionResponseDto;
import ro.uaic.info.parcel.paymentservice.domain.exception.*;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(value = {AwbAlreadyPayedException.class})
    protected ResponseEntity<ExceptionResponseDto> handleAwbAlreadyPayedException(Exception exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(value = {AwbDoesNotExistException.class})
    protected ResponseEntity<ExceptionResponseDto> handleAwbDoesNotExistException(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(value = {AwbIsNotYoursException.class})
    protected ResponseEntity<ExceptionResponseDto> handleAwbIsNotYoursException(Exception exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ExceptionResponseDto> handleGenericException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponseDto(exception.getMessage()));
    }
}
