package com.mustafaqasimov.fleettrack.repository;

import com.mustafaqasimov.fleettrack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    List<User> findAllByRole(com.mustafaqasimov.fleettrack.enums.Role role);
}
