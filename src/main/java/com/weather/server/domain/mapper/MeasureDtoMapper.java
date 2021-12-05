package com.weather.server.domain.mapper;

import com.weather.server.domain.dto.MeasureDto;
import com.weather.server.domain.entity.Measure;
import com.weather.server.domain.model.ISODate;

public class MeasureDtoMapper {
    public MeasureDto mapToDto(Measure measure){


        MeasureDto measureDto = new MeasureDto("", measure.stationId, ISODate.toString(measure.date),measure.temp,measure.humidity,measure.pressure,measure.pm25,
                measure.pm10,measure.pm25Corr);
        return measureDto;
    }
}
