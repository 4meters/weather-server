package com.weather.server.service;

import com.weather.server.domain.dto.chart.ChartListDto;
import com.weather.server.domain.dto.measure.*;

import java.util.List;

public interface MeasureService {
    boolean saveMeasure(NewMeasureDto newMeasureDto);
    boolean verifyStationId(String stationId);

    LastMeasureDto getLastMeasure(String stationId);
    MeasureListDto getMeasureListByDate(MeasureByDateDto measureByDateDto);
    ChartListDto getMeasuresForChart(MeasureByDateChartDto measureByDateChartDto);

    boolean saveMeasurePackage(List<NewMeasureDto> measureList);

    MeasureListDto getMeasureDatabase();

    LastMeasureListDto getLastMeasureAllPublic();
    LastMeasureListDto getLastMeasureAllPrivate(String token);

    float getElevation(String lat, String lng);

}
