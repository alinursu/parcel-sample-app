package ro.uaic.info.parcel.deliveryservice.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.uaic.info.parcel.database_management_service.domain.Awb;
import ro.uaic.info.parcel.deliveryservice.domain.dto.CreateAwbDto;

import java.util.List;

@RequestMapping("/api/v1/awb")
public interface AwbApi {
    @GetMapping
    @Operation(summary = "Lists all AWBs of the logged-in user.")
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
    ResponseEntity<List<Awb>> getAwbsOfUser(HttpServletRequest request) throws Exception;

    @GetMapping("/nonDelivered")
    @Operation(summary = "Lists all non-DELIVERED AWBs")
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
    ResponseEntity<List<Awb>> getAllNonDeliveredAwbs() throws Exception;

    @GetMapping("/{uniqueNumber}")
    @Operation(summary = "Gets an AWB registered in database, identified by unique awb number, whose owner is the logged-in user.")
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
    ResponseEntity<Awb> getAwbOfUserByUniqueNumber(HttpServletRequest request, @PathVariable("uniqueNumber") String uniqueNumber) throws Exception;

    @PostMapping
    @Operation(summary = "Registers a new AWB in the database")
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
    ResponseEntity<Awb> createAwb(CreateAwbDto dto, HttpServletRequest request) throws Exception;
}
