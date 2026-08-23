package com.mustafaqasimov.fleettrack.repository;

import com.mustafaqasimov.fleettrack.entity.Vehicle;
import com.mustafaqasimov.fleettrack.enums.ActiveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {

    List<Vehicle> findAllByActive(ActiveStatus active);

    Optional<Vehicle> findByIdAndActive(Long id, ActiveStatus active);

    boolean existsByLicensePlate(String licensePlate);

    List<Vehicle> findAllByNextServiceDueLessThanEqual(LocalDate date);
}
