package com.mustafaqasimov.fleettrack.service;

import com.mustafaqasimov.fleettrack.entity.Vehicle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaintenanceReminderMailService {

    private final JavaMailSender mailSender;

    public void sendReminder(String toEmail, List<Vehicle> dueVehicles) {
        try {
            String vehicleList = dueVehicles.stream()
                    .map(v -> "- %s %s (%s), servis tarixi: %s".formatted(
                            v.getMake(), v.getModel(), v.getLicensePlate(), v.getNextServiceDue()))
                    .collect(Collectors.joining("\n"));

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("FleetTrack: " + dueVehicles.size() + " maşının servis vaxtı çatıb");
            message.setText("Aşağıdakı maşınların texniki baxım vaxtı çatıb və ya yaxınlaşır:\n\n" + vehicleList);

            mailSender.send(message);
            log.info("Maintenance reminder email sent to {} for {} vehicles", toEmail, dueVehicles.size());

        } catch (Exception e) {
            log.error("Failed to send maintenance reminder email to {}", toEmail, e);
        }
    }
}
