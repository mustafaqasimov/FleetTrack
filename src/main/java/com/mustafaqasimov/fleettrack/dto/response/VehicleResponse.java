package com.mustafaqasimov.fleettrack.dto.response;

import com.mustafaqasimov.fleettrack.enums.VehicleStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Schema(description = "Vehicle response DTO")
public class VehicleResponse {
    @Schema(description = "The ID of the vehicle")
    Long id;
    @Schema(description = "The make of the vehicle", example = "Toyota")
    String make;
    @Schema(description = "The model of the vehicle", example = "Camry")
    String model;
    @Schema(description = "The year of the vehicle", example = "2020")
    Integer year;
    @Schema(description = "The license plate of the vehicle", example = "ABC123")
    String licensePlate;
    @Schema(description = "The status of the vehicle", example = "AVAILABLE")
    VehicleStatus status;
    @Schema(description = "The last known latitude of the vehicle", example = "37.7749")
    Double lastLatitude;
    @Schema(description = "The last known longitude of the vehicle", example = "-122.4194")
    Double lastLongitude;
    @Schema(description = "The timestamp of the last location update")
    LocalDateTime lastLocationAt;
}
