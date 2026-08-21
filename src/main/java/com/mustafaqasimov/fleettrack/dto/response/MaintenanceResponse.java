package com.mustafaqasimov.fleettrack.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Schema(description = "DTO for maintenance response")
public class MaintenanceResponse {
    @Schema(description = "The ID of the maintenance record")
    Long id;
    @Schema(description = "The ID of the vehicle")
    Long vehicleId;
    @Schema(description = "The license plate of the vehicle")
    String vehicleLicensePlate;
    @Schema(description = "The description of the maintenance work")
    String description;
    @Schema(description = "The date when the maintenance was performed")
    LocalDate serviceDate;
    @Schema(description = "The cost of the maintenance")
    BigDecimal cost;
}
