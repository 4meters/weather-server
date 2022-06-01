package com.weather.server.domain.mapper.admin;

import com.weather.server.domain.dto.station.StationDto;
import com.weather.server.domain.dto.station.StationListDto;
import com.weather.server.domain.dto.station.admin.StationDtoAdmin;
import com.weather.server.domain.dto.station.admin.StationListDtoAdmin;
import com.weather.server.domain.entity.Station;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StationListDtoMapperAdmin {
    StationDtoMapperAdmin stationDtoMapperAdmin = new StationDtoMapperAdmin();
    public StationListDtoAdmin mapToDto(List<Station> stationList){
        List<StationDtoAdmin> stationDtoList=new ArrayList<>();
        for(Station station : stationList){
            StationDtoAdmin stationDtoAdmin = stationDtoMapperAdmin.mapToDto(station);
            stationDtoList.add(stationDtoAdmin);
        }
        return new StationListDtoAdmin.Builder()
                .stationList(stationDtoList).build();
    }
}
