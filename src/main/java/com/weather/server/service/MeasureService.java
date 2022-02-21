package com.weather.server.service;

import com.weather.server.domain.dto.*;

import java.util.List;

public interface MeasureService {
    boolean saveMeasure(NewMeasureDto newMeasureDto);
    String verifyApiKey(String apiKey);
    boolean verifyStationId(String stationId);

    LastMeasureDto getLastMeasure();
    MeasureListDto getMeasureListByDate(MeasureByDateDto measureByDateDto);

    boolean saveMeasurePackage(List<NewMeasureDto> measureList);

    MeasureListDto getMeasureDatabase();
}
