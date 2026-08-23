package com.mustafaqasimov.fleettrack.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Schema(description = "Location broadcast message")
public class LocationBroadcastMessage {
    @Schema(description = "ID of the vehicle")
    Long vehicleId;
    @Schema(description = "License plate of the vehicle")
    String licensePlate;
    @Schema(description = "Latitude of the location")
    Double latitude;
    @Schema(description = "Longitude of the location")
    Double longitude;
    @Schema(description = "Timestamp when the location was recorded")
    LocalDateTime recordedAt;
}
