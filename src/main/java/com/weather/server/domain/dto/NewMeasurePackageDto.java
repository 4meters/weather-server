package com.weather.server.domain.dto;

import java.util.ArrayList;

public class NewMeasurePackageDto {
    private ArrayList<NewMeasureDto> measureList;

    public NewMeasurePackageDto(ArrayList<NewMeasureDto> measureList) {
        this.measureList = measureList;
    }

    public NewMeasurePackageDto() {
    }

    public ArrayList<NewMeasureDto> getMeasureList() {
        return measureList;
    }


    public void setMeasureList(ArrayList<NewMeasureDto> measureList) {
        this.measureList = measureList;
    }


    @Override
    public String toString() {
        return "NewMeasurePackageDto{" +
                "measureList=" + measureList +
                '}';
    }
}
