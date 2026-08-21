package com.mustafaqasimov.fleettrack.service;

import com.mustafaqasimov.fleettrack.dto.request.DriverRequest;
import com.mustafaqasimov.fleettrack.dto.response.DriverResponse;
import com.mustafaqasimov.fleettrack.entity.Driver;
import com.mustafaqasimov.fleettrack.entity.Vehicle;
import com.mustafaqasimov.fleettrack.enums.ActiveStatus;
import com.mustafaqasimov.fleettrack.exception.error.ResourceAlreadyExistsException;
import com.mustafaqasimov.fleettrack.exception.error.ResourceNotFoundException;
import com.mustafaqasimov.fleettrack.mapper.DriverMapper;
import com.mustafaqasimov.fleettrack.repository.DriverRepository;
import com.mustafaqasimov.fleettrack.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverService{

    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverMapper driverMapper;

    public DriverResponse create(DriverRequest request) {
        if (driverRepository.existsByLicenseNumber(request.getLicenseNumber())) {
            throw new ResourceAlreadyExistsException("A driver with this license number already exists");
        }

        Driver driver = driverMapper.toEntity(request);
        return driverMapper.toResponse(driverRepository.save(driver));
    }

    public DriverResponse getById(Long id) {
        return driverMapper.toResponse(findOrThrow(id));
    }

    public DriverResponse update(Long id, DriverRequest request) {
        Driver driver = findOrThrow(id);

        if (!driver.getLicenseNumber().equals(request.getLicenseNumber())
                && driverRepository.existsByLicenseNumber(request.getLicenseNumber())) {
            throw new ResourceAlreadyExistsException("A driver with this license number already exists");
        }

        driverMapper.updateEntityFromRequest(request, driver);
        return driverMapper.toResponse(driverRepository.save(driver));
    }

    public void delete(Long id) {
        Driver driver = findOrThrow(id);
        driver.setActive(ActiveStatus.INACTIVE);
        driverRepository.save(driver);
    }

    public DriverResponse assignVehicle(Long driverId, Long vehicleId) {
        Driver driver = findOrThrow(driverId);

        Vehicle vehicle = vehicleRepository.findByIdAndActive(vehicleId, ActiveStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: " + vehicleId));

        driver.setAssignedVehicle(vehicle);
        return driverMapper.toResponse(driverRepository.save(driver));
    }

    public DriverResponse unassignVehicle(Long driverId) {
        Driver driver = findOrThrow(driverId);
        driver.setAssignedVehicle(null);
        return driverMapper.toResponse(driverRepository.save(driver));
    }

    private Driver findOrThrow(Long id) {
        return driverRepository.findByIdAndActive(id, ActiveStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found: " + id));
    }
}
