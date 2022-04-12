package com.weather.server.domain.mapper;

import com.weather.server.domain.dto.chart.*;
import com.weather.server.domain.entity.MeasureAggregation;

import java.util.ArrayList;
import java.util.List;

public class ChartDtoMapper {
    public ChartListDto mapToDto(String chartValue, List<MeasureAggregation> measureList){
        ChartListDto chartListDto = null;
        switch(chartValue){
            case "temp":{
                List<ChartTempDto> chartList = new ArrayList<>();
                for(MeasureAggregation measureAggregation : measureList){
                    chartList.add(new ChartTempDto(measureAggregation.date, measureAggregation.temp.floatValue()));
                }
                chartListDto = new ChartListDto(chartList);
                break;
            }
            case "humidity":{
                List<ChartHumidityDto> chartList = new ArrayList<>();
                for(MeasureAggregation measureAggregation : measureList){
                    chartList.add(new ChartHumidityDto(measureAggregation.date, measureAggregation.humidity.floatValue()));
                }
                chartListDto = new ChartListDto(chartList);
                break;
            }
            case "pressure":{
                List<ChartPressureDto> chartList = new ArrayList<>();
                for(MeasureAggregation measureAggregation : measureList){
                    chartList.add(new ChartPressureDto(measureAggregation.date, measureAggregation.pressure.floatValue()));
                }
                chartListDto = new ChartListDto(chartList);
                break;
            }
            case "pm10":{
                List<ChartPM10Dto> chartList = new ArrayList<>();
                for(MeasureAggregation measureAggregation : measureList){
                    chartList.add(new ChartPM10Dto(measureAggregation.date, measureAggregation.pm10.floatValue()));
                }
                chartListDto = new ChartListDto(chartList);
                break;
            }
            case "pm25":{
                List<ChartPM25Dto> chartList = new ArrayList<>();
                for(MeasureAggregation measureAggregation : measureList){
                    chartList.add(new ChartPM25Dto(measureAggregation.date, measureAggregation.pm25.floatValue()));
                }
                chartListDto = new ChartListDto(chartList);
                break;
            }
            case "pm25Corr":{
                List<ChartPM25CorrDto> chartList = new ArrayList<>();
                for(MeasureAggregation measureAggregation : measureList){
                    chartList.add(new ChartPM25CorrDto(measureAggregation.date, measureAggregation.pm25Corr.floatValue()));
                }
                chartListDto = new ChartListDto(chartList);
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
