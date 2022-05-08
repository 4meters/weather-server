package com.weather.server.domain.dto.measure;

import com.weather.server.domain.entity.Measure;

import java.util.List;

public class MeasureListDto {
    private List<Measure> measureList;

    public MeasureListDto() {
    }

    private MeasureListDto(Builder builder) {
        this.measureList = builder.measureList;
    }

    public List<Measure> getMeasureList() {
        return measureList;
    }

    public void setMeasureList(List<Measure> measureList) {
        this.measureList = measureList;
    }

    public static final class Builder {
        private List<Measure> measureList;

        public Builder() {
        }

        public Builder measureList(List<Measure> measureList) {
            this.measureList = measureList;
            return this;
        }

        public MeasureListDto build() {
            return new MeasureListDto(this);
        }
    }
}
