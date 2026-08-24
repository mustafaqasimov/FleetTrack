package com.mustafaqasimov.fleettrack.notification;

import java.time.LocalDateTime;

public record VehicleOfflineMessage(Long vehicleId,
                                    String licensePlate,
                                    LocalDateTime lastLocationAt) {
}
