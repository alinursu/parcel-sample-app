package ro.uaic.info.parcel.database_management_service.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.parcel.database_management_service.domain.Awb;
import ro.uaic.info.parcel.database_management_service.domain.DeliveryRoute;

import java.util.List;

@RequestMapping("/api/v1/delivery")
public interface DeliveryRouteApi {
    @GetMapping
    @Operation(summary = "Lists all DeliveryRoutes registered in database.")
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
    ResponseEntity<List<DeliveryRoute>> getAllDeliveryRoutes();

    @GetMapping("/{id}")
    @Operation(summary = "Lists all DeliveryRoutes registered in database, bound to a given user (by id).")
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
    ResponseEntity<List<DeliveryRoute>> getAllDeliveryRoutesOfUser(@PathVariable("id") Long id);

    @PostMapping
    @Operation(summary = "Adds a DeliveryRoute in database")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DeliveryRoute.class))}
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
    ResponseEntity<DeliveryRoute> addDeliveryRoute(@RequestBody DeliveryRoute deliveryRoute);
}
