package com.mustafaqasimov.fleettrack.specification;

import com.mustafaqasimov.fleettrack.dto.request.VehicleFilterRequest;
import com.mustafaqasimov.fleettrack.entity.Vehicle;
import com.mustafaqasimov.fleettrack.enums.VehicleStatus;
import org.springframework.data.jpa.domain.Specification;

public class VehicleSpecification {

    public static Specification<Vehicle> hasStatus(VehicleStatus status) {
        return (root, query, cb) -> {
            if (status == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("status"), status);
        };
    }

    public static Specification<Vehicle> hasYear(Integer year) {
        return (root, query, cb) -> {
            if (year == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("year"), year);
        };
    }

    public static Specification<Vehicle> hasBrand(String brand) {
        return (root, query, cb) -> {
            if (brand == null || brand.isBlank()) {
                return cb.conjunction();
            }
            return cb.equal(cb.lower(root.get("make")), brand.toLowerCase());
        };
    }

    public static Specification<Vehicle> withFilters(VehicleFilterRequest filter) {
        return Specification
                .where(hasStatus(filter.getStatus()))
                .and(hasYear(filter.getModelYear()))
                .and(hasBrand(filter.getBrand()));
    }
}
