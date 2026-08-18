package com.mustafaqasimov.fleettrack.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Login request DTO")
public class LoginRequest {

    @Schema(description = "Username", example = "mustafa")
    @NotBlank(message = "Username is required")
    String userName;

    @Schema(description = "Password", example = "1234567")
    @NotBlank(message = "Password is required")
    String password;
}
