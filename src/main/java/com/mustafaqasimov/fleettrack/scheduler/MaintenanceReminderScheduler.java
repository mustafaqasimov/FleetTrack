package com.mustafaqasimov.fleettrack.scheduler;

import com.mustafaqasimov.fleettrack.entity.User;
import com.mustafaqasimov.fleettrack.entity.Vehicle;
import com.mustafaqasimov.fleettrack.enums.Role;
import com.mustafaqasimov.fleettrack.repository.UserRepository;
import com.mustafaqasimov.fleettrack.repository.VehicleRepository;
import com.mustafaqasimov.fleettrack.service.MaintenanceReminderMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MaintenanceReminderScheduler {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final MaintenanceReminderMailService mailService;

    // Hər gecə saat 00:00-da işə düşür
    @Scheduled(cron = "0 0 0 * * *")
    public void checkMaintenanceDue() {
        List<Vehicle> dueVehicles = vehicleRepository.findAllByNextServiceDueLessThanEqual(LocalDate.now());

        if (dueVehicles.isEmpty()) {
            log.info("Maintenance reminder check: no vehicles due for service");
            return;
        }

        List<User> admins = userRepository.findAllByRole(Role.ROLE_ADMIN);

        for (User admin : admins) {
            mailService.sendReminder(admin.getEmail(), dueVehicles);
        }

        log.info("Maintenance reminder check: {} vehicle(s) due, notified {} admin(s)",
                dueVehicles.size(), admins.size());
    }
}
