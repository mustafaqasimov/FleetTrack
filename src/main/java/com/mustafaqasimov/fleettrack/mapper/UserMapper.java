package com.mustafaqasimov.fleettrack.mapper;

import com.mustafaqasimov.fleettrack.dto.request.RegisterRequest;
import com.mustafaqasimov.fleettrack.dto.response.AuthResponse;
import com.mustafaqasimov.fleettrack.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "role", constant = "ROLE_FLEET_MANAGER")
    @Mapping(target = "password", source = "hashedPassword")
    User toEntity(RegisterRequest request, String hashedPassword);

    @Mapping(target = "token", source = "token")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.userName")
    @Mapping(target = "role", source = "user.role")
    AuthResponse toResponse(User user, String token);
}
