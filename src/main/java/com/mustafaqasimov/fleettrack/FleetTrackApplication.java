package com.mustafaqasimov.fleettrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FleetTrackApplication {

    public static void main(String[] args) {
        SpringApplication.run(FleetTrackApplication.class, args);
    }

}
