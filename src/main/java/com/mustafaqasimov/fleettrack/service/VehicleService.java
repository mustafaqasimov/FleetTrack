package com.mustafaqasimov.fleettrack.service;

import com.mustafaqasimov.fleettrack.dto.request.VehicleFilterRequest;
import com.mustafaqasimov.fleettrack.dto.request.VehicleRequest;
import com.mustafaqasimov.fleettrack.dto.response.VehicleResponse;
import com.mustafaqasimov.fleettrack.entity.Vehicle;
import com.mustafaqasimov.fleettrack.enums.ActiveStatus;
import com.mustafaqasimov.fleettrack.exception.error.ResourceAlreadyExistsException;
import com.mustafaqasimov.fleettrack.exception.error.ResourceNotFoundException;
import com.mustafaqasimov.fleettrack.mapper.VehicleMapper;
import com.mustafaqasimov.fleettrack.repository.VehicleRepository;
import com.mustafaqasimov.fleettrack.specification.VehicleSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    public VehicleResponse create(VehicleRequest request) {
        if (vehicleRepository.existsByLicensePlate(request.getLicensePlate())) {
            throw new ResourceAlreadyExistsException("A vehicle with this license plate already exists");
        }

        Vehicle vehicle = vehicleMapper.toEntity(request);
        return vehicleMapper.toResponse(vehicleRepository.save(vehicle));
    }

    public VehicleResponse getById(Long id) {
        return vehicleMapper.toResponse(findOrThrow(id));
    }

    public List<VehicleResponse> listActiveVehicles() {
        return vehicleRepository.findAllByActive(ActiveStatus.ACTIVE).stream()
                .map(vehicleMapper::toResponse)
                .collect(java.util.stream.Collectors.toList());
    }

    public VehicleResponse update(Long id, VehicleRequest request) {
        Vehicle vehicle = findOrThrow(id);

        if (!vehicle.getLicensePlate().equals(request.getLicensePlate())
                && vehicleRepository.existsByLicensePlate(request.getLicensePlate())) {
            throw new ResourceAlreadyExistsException("A vehicle with this license plate already exists");
        }

        vehicleMapper.updateEntityFromRequest(request, vehicle);
        return vehicleMapper.toResponse(vehicleRepository.save(vehicle));
    }

    public void delete(Long id) {
        Vehicle vehicle = findOrThrow(id);
        vehicle.setActive(ActiveStatus.INACTIVE);
        vehicleRepository.save(vehicle);
    }

    public Page<VehicleResponse> search(VehicleFilterRequest filter, Pageable pageable) {
        return vehicleRepository.findAll(VehicleSpecification.withFilters(filter), pageable)
                .map(vehicleMapper::toResponse);
    }

    private Vehicle findOrThrow(Long id) {
        return vehicleRepository.findByIdAndActive(id, ActiveStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: " + id));
    }
}

