package com.mustafaqasimov.fleettrack.mapper;

import com.mustafaqasimov.fleettrack.dto.request.DriverRequest;
import com.mustafaqasimov.fleettrack.dto.response.DriverResponse;
import com.mustafaqasimov.fleettrack.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DriverMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "assignedVehicle", ignore = true)
    Driver toEntity(DriverRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "assignedVehicle", ignore = true)
    void updateEntityFromRequest(DriverRequest request, @MappingTarget Driver driver);

    @Mapping(target = "assignedVehicleId", source = "assignedVehicle.id")
    @Mapping(target = "assignedVehiclePlate", source = "assignedVehicle.licensePlate")
    DriverResponse toResponse(Driver driver);
}
