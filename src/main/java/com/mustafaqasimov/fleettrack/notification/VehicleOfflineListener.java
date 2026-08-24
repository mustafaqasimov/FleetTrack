package com.mustafaqasimov.fleettrack.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mustafaqasimov.fleettrack.entity.User;
import com.mustafaqasimov.fleettrack.enums.Role;
import com.mustafaqasimov.fleettrack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class VehicleOfflineListener implements MessageListener {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            VehicleOfflineMessage offlineMessage =
                    objectMapper.readValue(message.getBody(), VehicleOfflineMessage.class);

            log.warn("Vehicle offline detected: {} ({})",
                    offlineMessage.licensePlate(), offlineMessage.vehicleId());

            notifyAdmins(offlineMessage);

        } catch (Exception e) {
            log.error("Failed to process vehicle offline message", e);
        }
    }

    private void notifyAdmins(VehicleOfflineMessage offlineMessage) {
        List<User> admins = userRepository.findAllByRole(Role.ROLE_ADMIN);

        if (admins.isEmpty()) {
            log.warn("Bildiriş göndərmək üçün heç bir admin tapılmadı.");
            return;
        }

        String[] adminEmails = admins.stream()
                .map(User::getEmail)
                .toArray(String[]::new);

        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(adminEmails);
            mail.setSubject("FleetTrack: Maşın oflayn oldu - " + offlineMessage.licensePlate());
            mail.setText("Maşın " + offlineMessage.licensePlate() +
                    " son " + offlineMessage.lastLocationAt() + " tarixindən GPS siqnalı göndərmir.");

            mailSender.send(mail);
            log.info("{} adminə oflayn xəbərdarlığı göndərildi.", adminEmails.length);

        } catch (Exception e) {
            log.error("Failed to send offline notification emails", e);
        }
    }
}
