package com.weather.server.service;

import com.weather.server.domain.dto.station.AddStationDto;
import com.weather.server.domain.dto.station.StationNameDto;
import com.weather.server.domain.dto.station.VerifyStationDto;
import com.weather.server.domain.dto.user.UserStationListDto;
import com.weather.server.domain.entity.Station;

import java.util.List;

public interface StationService {


    //test only!
    List<Station> getStationList(String token);

    List<Station> getPublicStationList();

    StationNameDto getStationName(String stationId);
    boolean verifyStationId(VerifyStationDto verifyStationDto);

    boolean addStationOnMap(AddStationDto addStationDto);

    UserStationListDto getUserStationList(String token);
}
