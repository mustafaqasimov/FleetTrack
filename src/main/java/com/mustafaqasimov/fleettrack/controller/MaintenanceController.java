package com.mustafaqasimov.fleettrack.controller;

import com.mustafaqasimov.fleettrack.dto.request.MaintenanceRequest;
import com.mustafaqasimov.fleettrack.dto.response.MaintenanceResponse;
import com.mustafaqasimov.fleettrack.service.MaintenanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maintenance")
@RequiredArgsConstructor
@Tag(name = "Maintenance",description = "Endpoints for managing maintenance records")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @Operation(summary = "Log a maintenance record",description = "Creates a new maintenance record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Maintenance record created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping
    public ResponseEntity<MaintenanceResponse> create(@Valid @RequestBody MaintenanceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(maintenanceService.create(request));
    }

    @Operation(summary = "Get a maintenance record by id",description = "Retrieves a maintenance record by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Maintenance record found"),
            @ApiResponse(responseCode = "404", description = "Maintenance record not found")
    })
    @GetMapping("/{id}")
    public MaintenanceResponse getById(@PathVariable Long id) {
        return maintenanceService.getById(id);
    }

    @Operation(summary = "Get all maintenance records for a vehicle",description = "Retrieves all maintenance records for a specific vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Maintenance records found"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @GetMapping("/vehicle/{vehicleId}")
    public List<MaintenanceResponse> getByVehicleId(@PathVariable Long vehicleId) {
        return maintenanceService.getByVehicleId(vehicleId);
    }

    @Operation(summary = "Delete a maintenance record",description = "Deletes a maintenance record by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Maintenance record deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Maintenance record not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        maintenanceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
