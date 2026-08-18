package com.mustafaqasimov.fleettrack.security;

import com.mustafaqasimov.fleettrack.entity.User;
import com.mustafaqasimov.fleettrack.exception.error.ResourceNotFoundException;
import com.mustafaqasimov.fleettrack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        Long userId = getCurrentUserId();
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public Long getCurrentUserId() {
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
