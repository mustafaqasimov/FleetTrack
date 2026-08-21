package com.mustafaqasimov.fleettrack.controller;

import com.mustafaqasimov.fleettrack.dto.request.VehicleFilterRequest;
import com.mustafaqasimov.fleettrack.dto.request.VehicleRequest;
import com.mustafaqasimov.fleettrack.dto.response.VehicleResponse;
import com.mustafaqasimov.fleettrack.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
@Tag(name = "Vehicles", description = "Vehicle management endpoints")
public class VehicleController {

    private final VehicleService vehicleService;

    @Operation(summary = "Register a new vehicle", description = "Creates a new vehicle record")
    @PostMapping
    public ResponseEntity<VehicleResponse> create(@Valid @RequestBody VehicleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.create(request));
    }

    @Operation(summary = "Get a vehicle by id", description = "Retrieves a vehicle record by its ID")
    @GetMapping("/{id}")
    public VehicleResponse getById(@PathVariable Long id) {
        return vehicleService.getById(id);
    }

    @Operation(summary = "List active vehicles", description = "Retrieves a list of all active vehicles")
    @GetMapping
    public List<VehicleResponse> listActiveVehicles() {
        return vehicleService.listActiveVehicles();
    }

    @Operation(summary = "Update a vehicle", description = "Updates an existing vehicle record")
    @PutMapping("/{id}")
    public VehicleResponse update(@PathVariable Long id, @Valid @RequestBody VehicleRequest request) {
        return vehicleService.update(id, request);
    }

    @Operation(summary = "Delete a vehicle", description = "Deletes a vehicle record by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vehicleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search vehicles with optional filters",
            description = "Searches for vehicles based on optional filters")
    @GetMapping
    public Page<VehicleResponse> search(
            @ModelAttribute VehicleFilterRequest filter,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return vehicleService.search(filter, pageable);
    }
}
