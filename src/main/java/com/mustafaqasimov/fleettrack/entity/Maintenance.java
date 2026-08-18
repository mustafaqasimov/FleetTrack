package com.mustafaqasimov.fleettrack.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "maintenance")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Maintenance extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    Vehicle vehicle;

    @Column(name = "description", length = 500)
    String description;

    @Column(name = "service_date", nullable = false)
    LocalDate serviceDate;

    @Column(name = "cost", precision = 10, scale = 2)
    BigDecimal cost;
}
