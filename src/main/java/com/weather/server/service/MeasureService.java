package com.weather.server.service;

import com.weather.server.domain.dto.MeasureByDateDto;
import com.weather.server.domain.dto.MeasureDto;
import com.weather.server.domain.dto.MeasureListDto;

public interface MeasureService {
    boolean saveMeasure(MeasureDto measureDto);
    String verifyApiKey(String apiKey);

    MeasureDto getLastMeasure();
    MeasureListDto getMeasureListByDate(MeasureByDateDto measureByDateDto);
}
