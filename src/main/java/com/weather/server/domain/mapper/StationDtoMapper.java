package com.weather.server.domain.mapper;

import com.weather.server.domain.dto.station.StationDto;
import com.weather.server.domain.entity.Station;
import org.springframework.stereotype.Component;

@Component
public class StationDtoMapper {
    public StationDto mapToDto(Station station){
        return new StationDto.Builder()
                .stationId(station.getStationId())
                .stationName(station.getStationName())
                .lat(station.getLat())
                .lng(station.getLng())
                .isActive(station.getActive())
                .measureInterval(station.getMeasureInterval()).build();
    }
}
