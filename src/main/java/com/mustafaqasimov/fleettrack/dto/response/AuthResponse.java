package com.mustafaqasimov.fleettrack.dto.response;

import com.mustafaqasimov.fleettrack.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Schema(description = "Authentication response DTO")
public class AuthResponse {
    @Schema(description = "JWT token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    String token;
    @Schema(description = "User ID", example = "1")
    Long userId;
    @Schema(description = "Username", example = "mustafa")
    String userName;
    @Schema(description = "User role", example = "ROLE_FLEET_MANAGER")
    Role role;
}
