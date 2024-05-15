package com.uber.location.services.impl;

import com.uber.location.dtos.NearbyDriversDto;
import com.uber.location.dtos.SaveDriverLocationDto;
import com.uber.location.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DriverLocationServiceImpl implements LocationService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final String DRIVER_COLLECTION_KEY = "drivers";

    private final int SEARCH_RADIUS = 5;
    @Override
    public boolean saveDriverLocation(SaveDriverLocationDto saveDriverLocationDto) {
        GeoOperations<String, String> geoOps = stringRedisTemplate.opsForGeo();

        geoOps.add(DRIVER_COLLECTION_KEY, new RedisGeoCommands.GeoLocation<>(
                saveDriverLocationDto.getDriverId(),
                new Point(saveDriverLocationDto.getLatitude(),
                        saveDriverLocationDto.getLongitude())
        ));

        return true;
    }

    @Override
    public List<SaveDriverLocationDto> getNearByDrivers(NearbyDriversDto nearbyDriversDto) {
        GeoOperations<String, String> geoOps = stringRedisTemplate.opsForGeo();

        Distance distance = new Distance(SEARCH_RADIUS, Metrics.KILOMETERS);
        Circle span = new Circle(new Point(nearbyDriversDto.getLatitude(), nearbyDriversDto.getLongitude()),
                distance);

        GeoResults<RedisGeoCommands.GeoLocation<String>> drivers = geoOps.radius(DRIVER_COLLECTION_KEY, span);
        List<SaveDriverLocationDto> driverIds = new ArrayList<>();

        for(GeoResult<RedisGeoCommands.GeoLocation<String>> result : drivers) {
            Point point = geoOps.position(DRIVER_COLLECTION_KEY, result.getContent().getName()).get(0);
            SaveDriverLocationDto dto = SaveDriverLocationDto.builder().build();
            dto.setDriverId(result.getContent().getName());
            dto.setLatitude(point.getX());
            dto.setLongitude(point.getY());
            driverIds.add(dto);
        }

        return driverIds;
    }
}
