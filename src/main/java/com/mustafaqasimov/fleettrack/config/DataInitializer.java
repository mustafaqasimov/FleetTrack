package com.mustafaqasimov.fleettrack.config;

import com.mustafaqasimov.fleettrack.entity.User;
import com.mustafaqasimov.fleettrack.enums.Role;
import com.mustafaqasimov.fleettrack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (userRepository.existsByUserName(adminUsername)) {
            log.info("Admin user '{}' already exists, skipping initialization", adminUsername);
            return;
        }

        User admin = User.builder()
                .userName(adminUsername)
                .email(adminEmail)
                .password(passwordEncoder.encode(adminPassword))
                .role(Role.ROLE_ADMIN)
                .build();

        userRepository.save(admin);
        log.info("Initial admin user '{}' created", adminUsername);
    }
}
