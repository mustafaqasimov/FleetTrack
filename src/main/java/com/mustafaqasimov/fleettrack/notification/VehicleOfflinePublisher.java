package com.mustafaqasimov.fleettrack.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mustafaqasimov.fleettrack.config.RedisPubSubConfig;
import com.mustafaqasimov.fleettrack.entity.Vehicle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VehicleOfflinePublisher {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public void publish(Vehicle vehicle) {
        try {
            String message = objectMapper.writeValueAsString(
                    new VehicleOfflineMessage(vehicle.getId(), vehicle.getLicensePlate(), vehicle.getLastLocationAt())
            );
            redisTemplate.convertAndSend(RedisPubSubConfig.VEHICLE_OFFLINE_CHANNEL, message);
        } catch (Exception e) {
            log.error("Failed to publish offline notification for vehicle {}", vehicle.getId(), e);
        }
    }
}
