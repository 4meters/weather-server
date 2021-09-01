package com.weather.server.service;

import com.weather.server.domain.dto.MeasureDto;

public interface MeasureService {
    boolean saveMeasure(MeasureDto measureDto);
    String verifyApiKey(String apiKey);

    MeasureDto getLastMeasure();
}
