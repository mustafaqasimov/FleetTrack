package com.mustafaqasimov.fleettrack.mapper;

import com.mustafaqasimov.fleettrack.dto.request.MaintenanceRequest;
import com.mustafaqasimov.fleettrack.dto.response.MaintenanceResponse;
import com.mustafaqasimov.fleettrack.entity.Maintenance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MaintenanceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "serviceDate", source = "request.serviceDate")
    @Mapping(target = "cost", source = "request.cost")
    Maintenance toEntity(MaintenanceRequest request);

    @Mapping(target = "vehicleId", source = "vehicle.id")
    @Mapping(target = "vehicleLicensePlate", source = "vehicle.licensePlate")
    MaintenanceResponse toResponse(Maintenance maintenance);
}
