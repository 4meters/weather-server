package com.weather.server.domain.mapper;

import com.weather.server.domain.dto.measure.NewMeasureDto;
import com.weather.server.domain.entity.Measure;
import com.weather.server.domain.model.ISODate;

public class LastMeasureDtoMapper {
    public NewMeasureDto mapToDto(Measure measure){


        NewMeasureDto newMeasureDto = new NewMeasureDto.Builder()
                .apiKey("")
                .stationId(measure.stationId)
                .date(ISODate.toString(measure.date))
                .temp(measure.temp.toString())
                .humidity(measure.humidity.toString())
                .pm10(measure.pm10.toString())
                .pm25(measure.pm25.toString())
                .pm25Corr(measure.pm25Corr.toString())
                .pressure(measure.pressure.toString()).build();


        //"", measure.stationId, ISODate.toString(measure.date),measure.temp,measure.humidity,measure.pressure,measure.pm25,
        //        measure.pm10,measure.pm25Corr);
        return newMeasureDto;
    }
}
