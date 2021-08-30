package com.weather.server.domain.mapper;

import com.weather.server.domain.dto.MeasureDto;
import com.weather.server.domain.entity.Measure;

public class MeasureMapper {
    public Measure mapToEntity(MeasureDto measureDto, String userId){
        Measure measure = new Measure();
        measure.setUserId(userId);
        measure.setTimestamp(measureDto.getTimestamp());
        measure.setTemp(measureDto.getTemp());
        measure.setPressure(measureDto.getPressure());
        measure.setHumidity(measureDto.getHumidity());
        measure.setPm10(measureDto.getPm10());
        measure.setPm25(measureDto.getPm25());
        measure.setPm25Corr(measureDto.getPm25Corr());
        return measure;
    }
}
