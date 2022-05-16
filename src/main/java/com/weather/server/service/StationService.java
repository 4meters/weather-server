package com.weather.server.service;

import com.weather.server.domain.dto.admin.RemoveStationDto;
import com.weather.server.domain.dto.station.StationChangeNameDto;
import com.weather.server.domain.dto.station.StationSetVisibilityDto;
import com.weather.server.domain.dto.station.*;
import com.weather.server.domain.dto.user.UserMyStationListDetailsDto;
import com.weather.server.domain.dto.user.UserStationListDto;
import com.weather.server.domain.entity.Station;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StationService {


    //test only!
    StationListDto getStationList(String token);

    StationListDto getPublicStationList();

    StationNameDto getStationName(String stationId);
    boolean verifyStationId(String stationId);
    boolean verifyStationIdAndStationKey(VerifyStationDto verifyStationDto);

    boolean addStationOnMap(AddStationDto addStationDto);

    UserStationListDto getUserStationList(String token);

    boolean modeSwitch(StationModeSwitchDto stationModeSwitchDto);

    boolean setMeasureInterval(StationSetMeasureIntervalDto stationSetMeasureIntervalDto);

    StationCurrentModeDto getCurrentMode(String stationId);

    UserMyStationListDetailsDto getUserMyStationListDetails(String token);

    boolean changeStationName(StationChangeNameDto stationChangeNameDto);

    boolean setVisibility(StationSetVisibilityDto stationSetVisibilityDto);

    ResponseEntity<?> removeStation(RemoveStationDto removeStationDto);
}
