package com.mustafaqasimov.fleettrack.repository;

import com.mustafaqasimov.fleettrack.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

}
