package com.mustafaqasimov.fleettrack.scheduler;

import com.mustafaqasimov.fleettrack.entity.Vehicle;
import com.mustafaqasimov.fleettrack.enums.VehicleStatus;
import com.mustafaqasimov.fleettrack.notification.VehicleOfflinePublisher;
import com.mustafaqasimov.fleettrack.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OfflineVehicleChecker {

    private static final int OFFLINE_THRESHOLD_MINUTES = 5;

    private final VehicleRepository vehicleRepository;
    private final VehicleOfflinePublisher publisher;

    @Scheduled(fixedRate = 5 * 60 * 1000) // hər 5 dəqiqədə
    public void checkOfflineVehicles() {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(OFFLINE_THRESHOLD_MINUTES);

        List<Vehicle> offlineVehicles = vehicleRepository
                .findAllByStatusNotAndLastLocationAtBefore(VehicleStatus.OUT_OF_SERVICE, threshold);

        for (Vehicle vehicle : offlineVehicles) {
            publisher.publish(vehicle);
        }

        if (!offlineVehicles.isEmpty()) {
            log.info("Detected {} offline vehicle(s)", offlineVehicles.size());
        }
    }
}
