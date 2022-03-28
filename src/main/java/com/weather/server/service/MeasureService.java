package com.weather.server.service;

import com.weather.server.domain.dto.chart.ChartTempListDto;
import com.weather.server.domain.dto.measure.LastMeasureDto;
import com.weather.server.domain.dto.measure.MeasureByDateDto;
import com.weather.server.domain.dto.measure.MeasureListDto;
import com.weather.server.domain.dto.measure.NewMeasureDto;

import java.util.List;

public interface MeasureService {
    boolean saveMeasure(NewMeasureDto newMeasureDto);
    String verifyApiKey(String apiKey);
    boolean verifyStationId(String stationId);

    LastMeasureDto getLastMeasure(String stationId);
    MeasureListDto getMeasureListByDate(MeasureByDateDto measureByDateDto);
    ChartTempListDto getMeasuresForChart(MeasureByDateDto measureByDateDto);

    boolean saveMeasurePackage(List<NewMeasureDto> measureList);

    MeasureListDto getMeasureDatabase();
}
