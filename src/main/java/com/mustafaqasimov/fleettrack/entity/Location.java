package com.mustafaqasimov.fleettrack.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "locations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Location extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    Vehicle vehicle;

    @Column(name = "latitude", nullable = false)
    Double latitude;

    @Column(name = "longitude", nullable = false)
    Double longitude;

    @CreationTimestamp
    @Column(name = "recorded_at")
    LocalDateTime recordedAt;
}
