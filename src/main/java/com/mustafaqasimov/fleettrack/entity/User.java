package com.mustafaqasimov.fleettrack.entity;

import com.mustafaqasimov.fleettrack.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@SuperBuilder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class User extends BaseEntity {

    @Column(name = "user_name", nullable = false, unique = true, length = 100)
    String userName;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    String email;

    @Column(name = "password", nullable = false, length = 200)
    String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @Builder.Default
    Role role = Role.ROLE_USER;
}
