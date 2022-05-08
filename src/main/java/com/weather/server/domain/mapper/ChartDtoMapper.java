package com.weather.server.domain.mapper;

import com.weather.server.domain.dto.chart.*;
import com.weather.server.domain.entity.MeasureAggregation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChartDtoMapper {
    public ChartListDto mapToDto(String chartValue, List<MeasureAggregation> measureList){
        ChartListDto chartListDto = null;
        switch(chartValue){
            case "temp":{
                List<ChartTempDto> chartList = new ArrayList<>();
                for(MeasureAggregation measureAggregation : measureList){
                    chartList.add(new ChartTempDto.Builder()
                            .date(measureAggregation.date)
                            .temp(measureAggregation.temp.floatValue())
                            .build()
                    );
                }
                chartListDto = new ChartListDto.Builder().chartDtoList(chartList).build();
                break;
            }
            case "humidity":{
                List<ChartHumidityDto> chartList = new ArrayList<>();
                for(MeasureAggregation measureAggregation : measureList){
                    chartList.add(new ChartHumidityDto.Builder()
                            .date(measureAggregation.date)
                            .humidity(measureAggregation.humidity.floatValue())
                            .build()
                    );
                }
                chartListDto = new ChartListDto.Builder().chartDtoList(chartList).build();
                break;
            }
            case "pressure":{
                List<ChartPressureDto> chartList = new ArrayList<>();
                for(MeasureAggregation measureAggregation : measureList){
                    chartList.add(new ChartPressureDto.Builder()
                            .date(measureAggregation.date)
                            .pressure(measureAggregation.pressure.floatValue())
                            .build()
                    );
                }
                chartListDto = new ChartListDto.Builder().chartDtoList(chartList).build();
                break;
            }
            case "pm10":{
                List<ChartPM10Dto> chartList = new ArrayList<>();
                for(MeasureAggregation measureAggregation : measureList){
                    chartList.add(new ChartPM10Dto.Builder()
                            .date(measureAggregation.date)
                            .pm10(measureAggregation.pm10.floatValue())
                            .build()
                    );
                }
                chartListDto = new ChartListDto.Builder().chartDtoList(chartList).build();
                break;
            }
            case "pm25":{
                List<ChartPM25Dto> chartList = new ArrayList<>();
                for(MeasureAggregation measureAggregation : measureList){
                    chartList.add(new ChartPM25Dto.Builder()
                            .date(measureAggregation.date)
                            .pm25(measureAggregation.pm25.floatValue())
                            .build()
                    );
                }
                chartListDto = new ChartListDto.Builder().chartDtoList(chartList).build();
                break;
            }
            case "pm25Corr":{
                List<ChartPM25CorrDto> chartList = new ArrayList<>();
                for(MeasureAggregation measureAggregation : measureList){
                    chartList.add(new ChartPM25CorrDto.Builder()
                            .date(measureAggregation.date)
                            .pm25Corr(measureAggregation.pm25Corr.floatValue())
                            .build()
                    );
                }
                chartListDto = new ChartListDto.Builder().chartDtoList(chartList).build();
                break;
            }
            default:{
                break;
            }
        }
        return chartListDto;

    }
    //measureList= measureRepository.findMaxTempByDateBeetweenGroupByHour(dateFrom, dateTo, stationId);
    //measureList.add(measureRepository.findByDateBetweenPipeline());
}
