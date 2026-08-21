package com.mustafaqasimov.fleettrack.repository;

import com.mustafaqasimov.fleettrack.entity.Maintenance;
import com.mustafaqasimov.fleettrack.enums.ActiveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    List<Maintenance> findAllByVehicleIdAndActive(Long vehicleId, ActiveStatus activeStatus);

    Optional<Maintenance> findByIdAndActive(Long id, ActiveStatus activeStatus);
}
