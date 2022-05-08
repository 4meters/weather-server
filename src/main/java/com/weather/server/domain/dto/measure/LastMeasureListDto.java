package com.weather.server.domain.dto.measure;

import com.weather.server.domain.entity.Measure;

import java.util.HashMap;

public class LastMeasureListDto {
    private HashMap<String, Measure> measureList;

    public LastMeasureListDto() {
    }

    private LastMeasureListDto(Builder builder) {
        this.measureList = builder.measureList;
    }

    public HashMap<String, Measure> getMeasureList() {
        return measureList;
    }

    public static final class Builder {
        private HashMap<String, Measure> measureList;

        public Builder() {
        }

        public static Builder aLastMeasureListDto() {
            return new Builder();
        }

        public Builder measureList(HashMap<String, Measure> measureList) {
            this.measureList = measureList;
            return this;
        }

        public LastMeasureListDto build() {
            return new LastMeasureListDto(this);
        }
    }
}
