package com.mustafaqasimov.fleettrack.service;

import com.mustafaqasimov.fleettrack.dto.request.MaintenanceRequest;
import com.mustafaqasimov.fleettrack.dto.response.MaintenanceResponse;
import com.mustafaqasimov.fleettrack.entity.Maintenance;
import com.mustafaqasimov.fleettrack.entity.Vehicle;
import com.mustafaqasimov.fleettrack.exception.error.ResourceNotFoundException;
import com.mustafaqasimov.fleettrack.mapper.MaintenanceMapper;
import com.mustafaqasimov.fleettrack.repository.MaintenanceRepository;
import com.mustafaqasimov.fleettrack.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaintenanceServiceTest {

    @Mock
    private MaintenanceRepository maintenanceRepository;
    @Mock private VehicleRepository vehicleRepository;
    @Mock private MaintenanceMapper maintenanceMapper;

    @InjectMocks
    private MaintenanceService maintenanceService;

    @Test
    void create_updatesVehicleNextServiceDue() {
        Vehicle vehicle = Vehicle.builder().id(1L).build();
        MaintenanceRequest request = new MaintenanceRequest(1L, "Oil change", LocalDate.of(2026, 1, 1), BigDecimal.TEN);
        Maintenance maintenance = Maintenance.builder().build();

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(maintenanceMapper.toEntity(request)).thenReturn(maintenance);
        when(maintenanceMapper.toResponse(maintenance)).thenReturn(MaintenanceResponse.builder().build());

        maintenanceService.create(request);

        assertEquals(LocalDate.of(2026, 4, 1), vehicle.getNextServiceDue());
        verify(vehicleRepository).save(vehicle);
    }

    @Test
    void create_throwsWhenVehicleNotFound() {
        MaintenanceRequest request = new MaintenanceRequest(99L, "Oil change", LocalDate.now(), BigDecimal.TEN);
        when(vehicleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> maintenanceService.create(request));
        verify(maintenanceRepository, never()).save(any());
    }
}
