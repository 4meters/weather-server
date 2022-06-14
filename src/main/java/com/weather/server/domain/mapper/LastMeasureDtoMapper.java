package com.weather.server.domain.mapper;

import com.weather.server.domain.dto.measure.LastMeasureDto;
import com.weather.server.domain.entity.Measure;
import com.weather.server.domain.model.ISODate;
import org.springframework.stereotype.Component;

@Component
public class LastMeasureDtoMapper {
    public LastMeasureDto mapToDto(Measure measure){


        LastMeasureDto lastMeasureDto = new LastMeasureDto.Builder()
                .stationId(measure.stationId)
                .date(ISODate.toString(measure.date))
                .temp(measure.temp.toString())
                .humidity(measure.humidity.toString())
                .pm10(measure.pm10.toString())
                .pm25(measure.pm25.toString())
                .pm25Corr(measure.pm25Corr.toString())
                .pressure(measure.pressure.toString()).build();


        return lastMeasureDto;
    }
}
