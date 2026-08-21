package com.mustafaqasimov.fleettrack.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Schema(description = "Driver response DTO")
public class DriverResponse {
    @Schema(description = "ID of the driver", example = "1")
    Long id;
    @Schema(description = "First name of the driver", example = "Mustafa")
    String firstName;
    @Schema(description = "Last name of the driver", example = "Qasimov")
    String lastName;
    @Schema(description = "Phone number of the driver", example = "123-456-7890")
    String phone;
    @Schema(description = "License number of the driver", example = "DL123456")
    String licenseNumber;
    @Schema(description = "ID of the assigned vehicle", example = "1")
    Long assignedVehicleId;
    @Schema(description = "Plate number of the assigned vehicle", example = "ABC123")
    String assignedVehiclePlate;
}
