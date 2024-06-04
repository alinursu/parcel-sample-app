package ro.uaic.info.parcel.database_management_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ro.uaic.info.parcel.database_management_service.domain.dto.ExceptionResponseDto;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ExceptionResponseDto> handleGenericException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponseDto(exception.getMessage()));
    }
}
