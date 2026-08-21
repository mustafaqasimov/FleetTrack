package com.mustafaqasimov.fleettrack.repository;

import com.mustafaqasimov.fleettrack.entity.Driver;
import com.mustafaqasimov.fleettrack.enums.ActiveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findAllByActive(ActiveStatus active);

    Optional<Driver> findByIdAndActive(Long id, ActiveStatus active);

    boolean existsByLicenseNumber(String licenseNumber);
}
