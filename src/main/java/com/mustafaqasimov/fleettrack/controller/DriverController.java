package com.mustafaqasimov.fleettrack.controller;

import com.mustafaqasimov.fleettrack.dto.request.DriverRequest;
import com.mustafaqasimov.fleettrack.dto.response.DriverResponse;
import com.mustafaqasimov.fleettrack.service.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
@Tag(name = "Drivers")
public class DriverController {

    private final DriverService driverService;

    @Operation(summary = "Register a new driver", description = "Creates a new driver record in the system")
    @PostMapping
    public ResponseEntity<DriverResponse> create(@Valid @RequestBody DriverRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(driverService.create(request));
    }

    @Operation(summary = "Get a driver by id",
            description = "Retrieves a driver record by its unique identifier")
    @GetMapping("/{id}")
    public DriverResponse getById(@PathVariable Long id) {
        return driverService.getById(id);
    }

    @Operation(summary = "Update a driver",
            description = "Updates the information of an existing driver")
    @PatchMapping("/{id}")
    public DriverResponse update(@PathVariable Long id, @Valid @RequestBody DriverRequest request) {
        return driverService.update(id, request);
    }

    @Operation(summary = "Delete a driver", description = "Removes a driver record from the system")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        driverService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Assign a vehicle to this driver",
            description = "Assigns a vehicle to the specified driver")
    @PatchMapping("/{driverId}/assign-vehicle/{vehicleId}")
    public DriverResponse assignVehicle(@PathVariable Long driverId, @PathVariable Long vehicleId) {
        return driverService.assignVehicle(driverId, vehicleId);
    }

    @Operation(summary = "Remove the vehicle assignment from this driver",
            description = "Removes the vehicle assignment from the specified driver")
    @PatchMapping("/{driverId}/unassign-vehicle")
    public DriverResponse unassignVehicle(@PathVariable Long driverId) {
        return driverService.unassignVehicle(driverId);
    }
}
