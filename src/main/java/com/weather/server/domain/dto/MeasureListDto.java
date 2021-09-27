package com.weather.server.domain.dto;

import com.weather.server.domain.entity.Measure;

import java.util.List;

public class MeasureListDto {
    private List<Measure> measureList;

    public MeasureListDto(List<Measure> measureList) {
        this.measureList = measureList;
    }

    public List<Measure> getMeasureList() {
        return measureList;
    }
}
