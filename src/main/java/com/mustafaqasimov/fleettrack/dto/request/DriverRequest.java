package com.mustafaqasimov.fleettrack.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "Driver request DTO")
public class DriverRequest {

    @Schema(description = "First name of the driver", example = "Mustafa")
    @NotBlank(message = "First name is required")
    String firstName;

    @Schema(description = "Last name of the driver", example = "Qasimov")
    @NotBlank(message = "Last name is required")
    String lastName;

    @Schema(description = "Phone number of the driver", example = "123-456-7890")
    String phone;

    @Schema(description = "License number of the driver", example = "DL123456")
    @NotBlank(message = "License number is required")
    String licenseNumber;

}
