package com.mustafaqasimov.fleettrack.controller;

import com.mustafaqasimov.fleettrack.dto.request.LocationUpdateRequest;
import com.mustafaqasimov.fleettrack.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
@Tag(name = "Location", description = "Endpoints for managing location records")
public class LocationController {

    private final LocationService locationService;

    @Operation(summary = "Submit a GPS location update for a vehicle - broadcasts live via WebSocket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location updated successfully",
                    content = @Content(schema = @Schema(implementation = LocationUpdateRequest.class))),
            @ApiResponse(responseCode = "400", description = "Invalid location data")
    })
    @PostMapping("/update")
    public ResponseEntity<Void> updateLocation(@Valid @RequestBody LocationUpdateRequest request) {
        locationService.updateLocation(request);
        return ResponseEntity.ok().build();
    }
}
