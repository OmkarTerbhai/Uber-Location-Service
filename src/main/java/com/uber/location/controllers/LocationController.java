package com.uber.location.controllers;

import com.uber.location.dtos.NearbyDriversDto;
import com.uber.location.dtos.SaveDriverLocationDto;
import com.uber.location.services.impl.DriverLocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {

    @Autowired
    private DriverLocationServiceImpl driverLocationService;

    @PostMapping("/drivers")
    public ResponseEntity<?> saveDriverLocation(@RequestBody SaveDriverLocationDto saveDriverLocationDto) {
        boolean res = this.driverLocationService.saveDriverLocation(saveDriverLocationDto);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/nearby")
    public ResponseEntity<List<SaveDriverLocationDto>> getNearByDrivers(@RequestBody NearbyDriversDto nearbyDriversDto) {
        System.out.println("Reached getNearByDrivers");
        List<SaveDriverLocationDto> response = this.driverLocationService.getNearByDrivers(nearbyDriversDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
