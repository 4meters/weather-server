package com.weather.server.domain.mapper.admin;

import com.weather.server.domain.dto.station.admin.StationDtoAdmin;
import com.weather.server.domain.entity.Station;
import org.springframework.stereotype.Component;

@Component
public class StationDtoMapperAdmin {
    public StationDtoAdmin mapToDto(Station station){
        return new StationDtoAdmin.Builder()
                .stationId(station.getStationId())
                .stationKey(station.getStationKey())
                .stationName(station.getStationName())
                .lat(station.getLat())
                .lng(station.getLng())
                .visible(station.getVisible())
                .isActive(station.getActive())
                .isAssigned(station.getAssigned())
                .measureInterval(station.getMeasureInterval()).build();
    }
}
