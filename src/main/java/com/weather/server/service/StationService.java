package com.weather.server.service;

import com.weather.server.domain.dto.station.*;
import com.weather.server.domain.dto.user.UserStationListDto;
import com.weather.server.domain.entity.Station;

import java.util.List;

public interface StationService {


    //test only!
    List<Station> getStationList(String token);

    List<Station> getPublicStationList();

    StationNameDto getStationName(String stationId);
    boolean verifyStationId(String stationId);
    boolean verifyStationIdAndStationKey(VerifyStationDto verifyStationDto);

    boolean addStationOnMap(AddStationDto addStationDto);

    UserStationListDto getUserStationList(String token);

    boolean modeSwitch(StationModeSwitchDto stationModeSwitchDto);

    boolean setMeasureInterval(StationSetMeasureIntervalDto stationSetMeasureIntervalDto);

    StationCurrentModeDto getCurrentMode(String stationId);
}
