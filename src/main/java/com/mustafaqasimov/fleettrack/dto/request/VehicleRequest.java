package com.mustafaqasimov.fleettrack.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Schema(description = "Vehicle request DTO")
public class VehicleRequest {

    @Schema(description = "The make of the vehicle", example = "Toyota")
    @NotBlank(message = "Make is required")
    String make;

    @Schema(description = "The model of the vehicle", example = "Camry")
    @NotBlank(message = "Model is required")
    String model;

    @Schema(description = "The year of the vehicle", example = "2020")
    @NotNull(message = "Year is required")
    @Min(value = 1980, message = "Year must be 1980 or later")
    @Max(value = 2100, message = "Year must be realistic")
    Integer year;

    @Schema(description = "The license plate of the vehicle", example = "ABC123")
    @NotBlank(message = "License plate is required")
    String licensePlate;
}
