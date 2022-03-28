package com.weather.server.service;

import com.weather.server.domain.dto.station.VerifyStationDto;
import com.weather.server.domain.entity.Station;

import java.util.List;

public interface StationService {


    List<Station> getStationList(String token);
    boolean verifyStationId(VerifyStationDto verifyStationDto);
}
