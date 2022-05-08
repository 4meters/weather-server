package com.weather.server.domain.dto.measure;

import java.util.ArrayList;

public class NewMeasurePackageDto {
    private ArrayList<NewMeasureDto> measureList;

    private NewMeasurePackageDto(Builder builder) {
        this.measureList = builder.measureList;
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

    public static final class Builder {
        private ArrayList<NewMeasureDto> measureList;

        private Builder() {
        }

        public static Builder aNewMeasurePackageDto() {
            return new Builder();
        }

        public Builder measureList(ArrayList<NewMeasureDto> measureList) {
            this.measureList = measureList;
            return this;
        }

        public NewMeasurePackageDto build() {
            return new NewMeasurePackageDto(this);
        }
    }
}
