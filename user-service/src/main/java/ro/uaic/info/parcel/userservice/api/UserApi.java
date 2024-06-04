package ro.uaic.info.parcel.userservice.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.parcel.database_management_service.domain.UserRole;
import ro.uaic.info.parcel.database_management_service.domain.dto.ExceptionResponseDto;
import ro.uaic.info.parcel.userservice.domain.dto.UserLoginDto;
import ro.uaic.info.parcel.userservice.domain.dto.UserLoginResponseDto;
import ro.uaic.info.parcel.userservice.domain.dto.UserRegisterDto;
import ro.uaic.info.parcel.userservice.domain.exception.*;
import ro.uaic.info.parcel.userservice.jwt.JwtTokenContent;

import java.util.List;

@RequestMapping("/api/v1/user")
public interface UserApi {
    @PostMapping("/login")
    @Operation(summary = "Login user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserLoginResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))}
            )
    })
    ResponseEntity<UserLoginResponseDto> loginUser(@RequestBody UserLoginDto dto) throws IncorrectCredentialsException, InvalidLoginDtoException, InvalidEmailAddressOnLoginException;

    @PostMapping("/register")
    @Operation(summary = "Register user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}
            ),
            @ApiResponse(
                    responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "401", description = "Unauthorized",
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
    ResponseEntity<String> registerUser(@RequestBody UserRegisterDto dto) throws EmailAlreadyInUseException, InvalidEmailAddressOnRegisterException, InvalidRegisterDtoException;

    @GetMapping("/decode")
    @Operation(summary = "Decode JWT token and get metadata")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}
            ),
            @ApiResponse(
                    responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))}
            )
    })
    ResponseEntity<JwtTokenContent> decodeJwtToken(@RequestParam("token") String token);

    @GetMapping("/roles")
    @Operation(summary = "Get the roles of a user, identified by email")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))}
            ),
            @ApiResponse(
                    responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}
            ),
            @ApiResponse(
                    responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))}
            )
    })
    ResponseEntity<List<UserRole>> getUserRoles(@RequestParam("email") String email) throws IncorrectCredentialsException;
}
