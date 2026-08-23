package com.mustafaqasimov.fleettrack.service;

import com.mustafaqasimov.fleettrack.dto.request.LocationUpdateRequest;
import com.mustafaqasimov.fleettrack.dto.response.LocationBroadcastMessage;
import com.mustafaqasimov.fleettrack.entity.Location;
import com.mustafaqasimov.fleettrack.entity.Vehicle;
import com.mustafaqasimov.fleettrack.exception.error.ResourceNotFoundException;
import com.mustafaqasimov.fleettrack.repository.LocationRepository;
import com.mustafaqasimov.fleettrack.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final VehicleRepository vehicleRepository;
    private final LocationRepository locationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public void updateLocation(LocationUpdateRequest request) {
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: " + request.getVehicleId()));

        LocalDateTime now = LocalDateTime.now();

        Location location = Location.builder()
                .vehicle(vehicle)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();
        locationRepository.save(location);

        vehicle.setLastLatitude(request.getLatitude());
        vehicle.setLastLongitude(request.getLongitude());
        vehicle.setLastLocationAt(now);
        vehicleRepository.save(vehicle);

        LocationBroadcastMessage message = LocationBroadcastMessage.builder()
                .vehicleId(vehicle.getId())
                .licensePlate(vehicle.getLicensePlate())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .recordedAt(now)
                .build();

        messagingTemplate.convertAndSend("/topic/vehicles/location", message);
    }
}
