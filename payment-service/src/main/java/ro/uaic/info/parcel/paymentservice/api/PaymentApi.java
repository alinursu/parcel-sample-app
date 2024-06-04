package ro.uaic.info.parcel.paymentservice.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.uaic.info.parcel.database_management_service.domain.dto.ExceptionResponseDto;
import ro.uaic.info.parcel.paymentservice.domain.exception.*;

@RequestMapping("/api/v1/payment")
public interface PaymentApi {
    @PostMapping("/{awbNumber}")
    @Operation(summary = "Pay a delivery order")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}
            ),
            @ApiResponse(
                    responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "409", description = "Conflict",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))}
            )
    })
    ResponseEntity<String> payAwbOrder(@PathVariable String awbNumber, HttpServletRequest request) throws AwbDoesNotExistException, AwbIsNotYoursException, AwbAlreadyPayedException;
}
