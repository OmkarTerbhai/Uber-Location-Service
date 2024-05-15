package com.uber.location.services;

import com.uber.location.dtos.NearbyDriversDto;
import com.uber.location.dtos.SaveDriverLocationDto;

import java.util.List;

public interface LocationService {

    public boolean saveDriverLocation(SaveDriverLocationDto saveDriverLocationDto);

    public List<SaveDriverLocationDto> getNearByDrivers(NearbyDriversDto nearbyDriversDto);
}
