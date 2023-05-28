package com.weather.server.domain.mapper;

import com.weather.server.domain.dto.measure.NewMeasureDto;
import com.weather.server.domain.entity.Measure;
import org.bson.types.Decimal128;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class NewMeasureMapper {

    public Measure mapToEntity(NewMeasureDto newMeasureDto){
        Measure measure = new Measure();
        measure.setStationId(newMeasureDto.getStationId());
        measure.setDate(Date.from(Instant.parse(newMeasureDto.getDate())));

        if(newMeasureDto.getTemp()!=null){
            measure.setTemp(Decimal128.parse(newMeasureDto.getTemp()));
        }
        if(newMeasureDto.getHumidity()!=null){
            measure.setPressure(Decimal128.parse(newMeasureDto.getPressure()));
        }
        if(newMeasureDto.getPressure()!=null){
            measure.setHumidity(Decimal128.parse(newMeasureDto.getHumidity()));
        }
        if(newMeasureDto.getPm10()!=null){
            measure.setPm10(Decimal128.parse(newMeasureDto.getPm10()));
        }
        if(newMeasureDto.getPm25()!=null){
            measure.setPm25(Decimal128.parse(newMeasureDto.getPm25()));
        }
        if(newMeasureDto.getPm25Corr()!=null){
            measure.setPm25Corr(Decimal128.parse(newMeasureDto.getPm25Corr()));
        }
        
        return measure;
    }
}
