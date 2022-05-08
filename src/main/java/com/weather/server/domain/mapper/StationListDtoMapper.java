package com.weather.server.domain.mapper;

import com.weather.server.domain.dto.station.StationDto;
import com.weather.server.domain.dto.station.StationListDto;
import com.weather.server.domain.entity.Station;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StationListDtoMapper {
    StationDtoMapper stationDtoMapper = new StationDtoMapper();
    public StationListDto mapToDto(List<Station> stationList){
        List<StationDto> stationDtoList=new ArrayList<>();
        for(Station station : stationList){
            StationDto stationDto = stationDtoMapper.mapToDto(station);
            stationDtoList.add(stationDto);
        }
        return new StationListDto.Builder()
                .stationList(stationDtoList).build();
    }
}
