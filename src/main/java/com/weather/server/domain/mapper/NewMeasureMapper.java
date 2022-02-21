package com.weather.server.domain.mapper;

import com.weather.server.domain.dto.NewMeasureDto;
import com.weather.server.domain.entity.Measure;

import java.time.Instant;
import java.util.Date;

public class NewMeasureMapper {
    public Measure mapToEntity(NewMeasureDto newMeasureDto){
        Measure measure = new Measure();
        measure.setStationId(newMeasureDto.getStationId());
        measure.setDate(Date.from( Instant.parse(newMeasureDto.getDate())));
        measure.setTemp(newMeasureDto.getTemp());
        measure.setPressure(newMeasureDto.getPressure());
        measure.setHumidity(newMeasureDto.getHumidity());
        measure.setPm10(newMeasureDto.getPm10());
        measure.setPm25(newMeasureDto.getPm25());
        measure.setPm25Corr(newMeasureDto.getPm25Corr());
        return measure;
    }
}
