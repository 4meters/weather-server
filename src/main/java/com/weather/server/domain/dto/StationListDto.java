package com.weather.server.domain.dto;

import com.weather.server.domain.entity.Measure;
import com.weather.server.domain.entity.Station;

import java.util.List;

public class StationListDto {
    private List<Station> stationList;

    private StationListDto(Builder builder) {
        this.stationList = builder.stationList;
    }

    public List<Station> getStationList() {
        return stationList;
    }

    public static final class Builder {
        private List<Station> stationList;

        public Builder() {
        }

        public Builder stationList(List<Station> stationList) {
            this.stationList = stationList;
            return this;
        }

        public StationListDto build() {
            return new StationListDto(this);
        }
    }
}
