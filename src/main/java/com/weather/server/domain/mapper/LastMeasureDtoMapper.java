package com.weather.server.domain.mapper;

import com.weather.server.domain.dto.NewMeasureDto;
import com.weather.server.domain.entity.Measure;
import com.weather.server.domain.model.ISODate;

public class LastMeasureDtoMapper {
    public NewMeasureDto mapToDto(Measure measure){


        NewMeasureDto newMeasureDto = new NewMeasureDto.Builder()
                .apiKey("")
                .stationID(measure.stationId)
                .date(ISODate.toString(measure.date))
                .temp(measure.temp)
                .humidity(measure.humidity)
                .pm10(measure.pm10)
                .pm25(measure.pm25)
                .pm25Corr(measure.pm25Corr)
                .pressure(measure.pressure).build();


        //"", measure.stationId, ISODate.toString(measure.date),measure.temp,measure.humidity,measure.pressure,measure.pm25,
        //        measure.pm10,measure.pm25Corr);
        return newMeasureDto;
    }
}
