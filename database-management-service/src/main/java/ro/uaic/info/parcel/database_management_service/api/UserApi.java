package ro.uaic.info.parcel.database_management_service.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.parcel.database_management_service.domain.User;
import ro.uaic.info.parcel.database_management_service.domain.dto.AddUserRequestDto;

@RequestMapping("/api/v1/user")
public interface UserApi {
    @GetMapping
    @Operation(summary = "Get user by email")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}
            ),
            @ApiResponse(
                    responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}
            ),
            @ApiResponse(
                    responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}
            )
    })
    ResponseEntity<User> getUserByEmail(@RequestParam String email);

    @PostMapping
    @Operation(summary = "Add user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}
            ),
            @ApiResponse(
                    responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}
            ),
            @ApiResponse(
                    responseCode = "409", description = "Conflict",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}
            ),
            @ApiResponse(
                    responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}
            )
    })
    ResponseEntity<User> addUser(@RequestBody AddUserRequestDto dto);
}
