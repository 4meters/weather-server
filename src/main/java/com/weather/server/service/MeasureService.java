package com.weather.server.service;

import com.weather.server.domain.dto.LastMeasureDto;
import com.weather.server.domain.dto.MeasureByDateDto;
import com.weather.server.domain.dto.NewMeasureDto;
import com.weather.server.domain.dto.MeasureListDto;

public interface MeasureService {
    boolean saveMeasure(NewMeasureDto newMeasureDto);
    String verifyApiKey(String apiKey);
    boolean verifyStationId(String stationId);

    LastMeasureDto getLastMeasure();
    MeasureListDto getMeasureListByDate(MeasureByDateDto measureByDateDto);
}
