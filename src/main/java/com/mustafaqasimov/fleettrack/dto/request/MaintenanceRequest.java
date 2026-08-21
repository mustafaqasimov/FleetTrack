package com.mustafaqasimov.fleettrack.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Schema(description = "DTO for maintenance request")
public class MaintenanceRequest {

    @Schema(description = "The ID of the vehicle",example = "1")
    @NotNull(message = "Vehicle id is required")
    Long vehicleId;

    @Schema(description = "The description of the maintenance work",example = "Oil change")
    @Size(max = 500, message = "Description must be at most 500 characters")
    String description;

    @Schema(description = "The date when the maintenance was performed",example = "2023-01-01")
    @NotNull(message = "Service date is required")
    @PastOrPresent(message = "Service date cannot be in the future")
    LocalDate serviceDate;

    @Schema(description = "The cost of the maintenance",example = "100.00")
    @DecimalMin(value = "0.0", message = "Cost cannot be negative")
    BigDecimal cost;
}
