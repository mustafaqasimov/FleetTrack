package com.mustafaqasimov.fleettrack.service;

import com.mustafaqasimov.fleettrack.dto.request.MaintenanceRequest;
import com.mustafaqasimov.fleettrack.dto.response.MaintenanceResponse;
import com.mustafaqasimov.fleettrack.entity.Maintenance;
import com.mustafaqasimov.fleettrack.entity.Vehicle;
import com.mustafaqasimov.fleettrack.enums.ActiveStatus;
import com.mustafaqasimov.fleettrack.exception.error.ResourceNotFoundException;
import com.mustafaqasimov.fleettrack.mapper.MaintenanceMapper;
import com.mustafaqasimov.fleettrack.repository.MaintenanceRepository;
import com.mustafaqasimov.fleettrack.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final VehicleRepository vehicleRepository;
    private final MaintenanceMapper maintenanceMapper;

    public MaintenanceResponse create(MaintenanceRequest request) {
        Vehicle vehicle = vehicleRepository.findByIdAndActive(request.getVehicleId(), ActiveStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: " + request.getVehicleId()));

        Maintenance maintenance = maintenanceMapper.toEntity(request);
        maintenance.setVehicle(vehicle);
        maintenanceRepository.save(maintenance);

        vehicle.setNextServiceDue(request.getServiceDate().plusMonths(3));
        vehicleRepository.save(vehicle);

        return maintenanceMapper.toResponse(maintenanceRepository.save(maintenance));
    }

    public MaintenanceResponse getById(Long id) {
        return maintenanceMapper.toResponse(findOrThrow(id));
    }

    public List<MaintenanceResponse> getByVehicleId(Long vehicleId) {
        return maintenanceRepository.findAllByVehicleIdAndActive(vehicleId, ActiveStatus.ACTIVE).stream()
                .map(maintenanceMapper::toResponse)
                .toList();
    }

    public void delete(Long id) {
        Maintenance maintenance = findOrThrow(id);
        maintenance.setActive(ActiveStatus.INACTIVE);
        maintenanceRepository.save(maintenance);
    }

    private Maintenance findOrThrow(Long id) {
        return maintenanceRepository.findByIdAndActive(id, ActiveStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance record not found: " + id));
    }
}
