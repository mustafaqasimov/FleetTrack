package com.mustafaqasimov.fleettrack.entity;

import com.mustafaqasimov.fleettrack.enums.VehicleStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "vehicles")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Vehicle extends BaseEntity {

    @Column(name = "make", nullable = false, length = 100)
    String make;

    @Column(name = "model", nullable = false, length = 100)
    String model;

    @Column(name = "year", nullable = false)
    Integer year;

    @Column(name = "license_plate", nullable = false, unique = true, length = 20)
    String licensePlate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    VehicleStatus status = VehicleStatus.AVAILABLE;

    @Column(name = "last_latitude")
    Double lastLatitude;

    @Column(name = "last_longitude")
    Double lastLongitude;

    @Column(name = "last_location_at")
    LocalDateTime lastLocationAt;

    @Column(name = "next_service_due")
    LocalDate nextServiceDue;
}
