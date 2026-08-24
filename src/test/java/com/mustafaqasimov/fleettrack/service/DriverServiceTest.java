package com.mustafaqasimov.fleettrack.service;

import com.mustafaqasimov.fleettrack.dto.response.DriverResponse;
import com.mustafaqasimov.fleettrack.entity.Driver;
import com.mustafaqasimov.fleettrack.entity.Vehicle;
import com.mustafaqasimov.fleettrack.exception.error.ResourceNotFoundException;
import com.mustafaqasimov.fleettrack.mapper.DriverMapper;
import com.mustafaqasimov.fleettrack.repository.DriverRepository;
import com.mustafaqasimov.fleettrack.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;
    @Mock private VehicleRepository vehicleRepository;
    @Mock private DriverMapper driverMapper;

    @InjectMocks
    private DriverService driverService;

    @Test
    void assignVehicle_throwsWhenVehicleNotFound() {
        Driver driver = Driver.builder().id(1L).build();
        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));
        when(vehicleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> driverService.assignVehicle(1L, 99L));
    }

    @Test
    void assignVehicle_setsVehicleOnDriver() {
        Driver driver = Driver.builder().id(1L).build();
        Vehicle vehicle = Vehicle.builder().id(5L).build();

        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));
        when(vehicleRepository.findById(5L)).thenReturn(Optional.of(vehicle));
        when(driverRepository.save(driver)).thenReturn(driver);
        when(driverMapper.toResponse(driver)).thenReturn(DriverResponse.builder().build());

        driverService.assignVehicle(1L, 5L);

        assertEquals(vehicle, driver.getAssignedVehicle());
    }

    @Test
    void unassignVehicle_clearsAssignedVehicle() {
        Vehicle vehicle = Vehicle.builder().id(5L).build();
        Driver driver = Driver.builder().id(1L).assignedVehicle(vehicle).build();

        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));
        when(driverRepository.save(driver)).thenReturn(driver);
        when(driverMapper.toResponse(driver)).thenReturn(DriverResponse.builder().build());

        driverService.unassignVehicle(1L);

        assertNull(driver.getAssignedVehicle());
    }
}
