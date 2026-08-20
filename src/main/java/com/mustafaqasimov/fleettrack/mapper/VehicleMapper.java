package com.mustafaqasimov.fleettrack.mapper;

import com.mustafaqasimov.fleettrack.dto.request.VehicleRequest;
import com.mustafaqasimov.fleettrack.dto.response.VehicleResponse;
import com.mustafaqasimov.fleettrack.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "lastLatitude", ignore = true)
    @Mapping(target = "lastLongitude", ignore = true)
    @Mapping(target = "lastLocationAt", ignore = true)
    Vehicle toEntity(VehicleRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "lastLatitude", ignore = true)
    @Mapping(target = "lastLongitude", ignore = true)
    @Mapping(target = "lastLocationAt", ignore = true)
    void updateEntityFromRequest(VehicleRequest request, @MappingTarget Vehicle vehicle);

    VehicleResponse toResponse(Vehicle vehicle);
}
