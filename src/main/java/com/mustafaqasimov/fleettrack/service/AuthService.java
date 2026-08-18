package com.mustafaqasimov.fleettrack.service;

import com.mustafaqasimov.fleettrack.dto.request.LoginRequest;
import com.mustafaqasimov.fleettrack.dto.request.RegisterRequest;
import com.mustafaqasimov.fleettrack.dto.response.AuthResponse;
import com.mustafaqasimov.fleettrack.entity.User;
import com.mustafaqasimov.fleettrack.exception.error.InvalidCredentialsException;
import com.mustafaqasimov.fleettrack.exception.error.ResourceAlreadyExistsException;
import com.mustafaqasimov.fleettrack.mapper.UserMapper;
import com.mustafaqasimov.fleettrack.repository.UserRepository;
import com.mustafaqasimov.fleettrack.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public void register(RegisterRequest request) {
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new ResourceAlreadyExistsException("Username already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already registered");
        }

        userRepository.save(
                userMapper.toEntity(request, passwordEncoder.encode(request.getPassword()))
        );
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        String token = jwtService.generateToken(user);
        return userMapper.toResponse(user, token);
    }
}
