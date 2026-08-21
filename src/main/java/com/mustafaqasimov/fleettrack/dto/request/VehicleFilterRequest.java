package com.mustafaqasimov.fleettrack.dto.request;

import com.mustafaqasimov.fleettrack.enums.VehicleStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Schema(description = "Request object for filtering vehicles")
public class VehicleFilterRequest {
    @Schema(description = "The status of the vehicle")
    VehicleStatus status;
    @Schema(description = "The model year of the vehicle")
    Integer modelYear;
    @Schema(description = "The brand of the vehicle")
    String brand;
}
