package com.mustafaqasimov.fleettrack.service;

import com.mustafaqasimov.fleettrack.dto.request.VehicleRequest;
import com.mustafaqasimov.fleettrack.dto.response.VehicleResponse;
import com.mustafaqasimov.fleettrack.entity.Vehicle;
import com.mustafaqasimov.fleettrack.exception.error.ResourceAlreadyExistsException;
import com.mustafaqasimov.fleettrack.exception.error.ResourceNotFoundException;
import com.mustafaqasimov.fleettrack.mapper.VehicleMapper;
import com.mustafaqasimov.fleettrack.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock private VehicleRepository vehicleRepository;
    @Mock
    private VehicleMapper vehicleMapper;

    @InjectMocks
    private VehicleService vehicleService;

    @Test
    void create_throwsWhenLicensePlateAlreadyExists() {
        VehicleRequest request = new VehicleRequest("Mercedes", "Sprinter", 2024, "10-AA-123");
        when(vehicleRepository.existsByLicensePlate("10-AA-123")).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> vehicleService.create(request));
        verify(vehicleRepository, never()).save(any());
    }

    @Test
    void update_allowsKeepingTheSameLicensePlate() {
        Vehicle existing = Vehicle.builder().id(1L).licensePlate("10-AA-123").build();
        VehicleRequest request = new VehicleRequest("Mercedes", "Sprinter", 2024, "10-AA-123");

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(vehicleRepository.save(existing)).thenReturn(existing);
        when(vehicleMapper.toResponse(existing)).thenReturn(VehicleResponse.builder().id(1L).build());

        vehicleService.update(1L, request);

        verify(vehicleRepository, never()).existsByLicensePlate(any());
    }

    @Test
    void update_throwsWhenChangingToAnAlreadyTakenPlate() {
        Vehicle existing = Vehicle.builder().id(1L).licensePlate("10-AA-123").build();
        VehicleRequest request = new VehicleRequest("Mercedes", "Sprinter", 2024, "20-BB-456");

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(vehicleRepository.existsByLicensePlate("20-BB-456")).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> vehicleService.update(1L, request));
        verify(vehicleRepository, never()).save(any());
    }

    @Test
    void getById_throwsWhenNotFound() {
        when(vehicleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> vehicleService.getById(99L));
    }
}