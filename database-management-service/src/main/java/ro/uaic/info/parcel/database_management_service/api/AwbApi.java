package ro.uaic.info.parcel.database_management_service.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.parcel.database_management_service.domain.Awb;
import ro.uaic.info.parcel.database_management_service.domain.User;
import ro.uaic.info.parcel.database_management_service.domain.dto.AddAwbRequestDto;

import java.util.List;

@RequestMapping("/api/v1/awb")
public interface AwbApi {
    @GetMapping
    @Operation(summary = "Lists all AWBs registered in database")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))}
            ),
            @ApiResponse(
                    responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}
            )
    })
    ResponseEntity<List<Awb>> getAllAwbs();

    @GetMapping("/{number}")
    @Operation(summary = "Gets an AWB registered in database, identified by unique awb number")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Awb.class))}
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
    ResponseEntity<Awb> getAwbByUniqueNumber(@PathVariable String number);

    @PostMapping
    @Operation(summary = "Adds an AWB in database.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Awb.class))}
            ),
            @ApiResponse(
                    responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}
            ),
            @ApiResponse(
                    responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}
            )
    })
    ResponseEntity<Awb> addAwb(@RequestBody AddAwbRequestDto dto);

    @PutMapping
    @Operation(summary = "Updates an AWB in database.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Awb.class))}
            ),
            @ApiResponse(
                    responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}
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
    ResponseEntity<Awb> updateAwb(@RequestBody Awb awb);
}
