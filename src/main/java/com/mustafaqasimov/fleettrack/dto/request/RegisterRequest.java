package com.mustafaqasimov.fleettrack.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Register request DTO")
public class RegisterRequest {

    @Schema(description = "The username of the user", example = "mustafa")
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50)
    String userName;

    @Schema(description = "The email of the user", example = "mustafa@example.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    String email;

    @Schema(description = "The password of the user", example = "password123")
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    String password;
}
