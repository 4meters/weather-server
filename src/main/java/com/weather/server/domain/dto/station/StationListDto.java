package com.weather.server.domain.dto.station;

import java.util.List;

public class StationListDto {
    private List<StationDto> stationList;



    private StationListDto(Builder builder) {
        this.stationList = builder.stationList;
    }

    public List<StationDto> getStationList() {
        return stationList;
    }

    public static final class Builder {
        private List<StationDto> stationList;

        public Builder() {
        }

        public Builder stationList(List<StationDto> stationList) {
            this.stationList = stationList;
            return this;
        }

        public StationListDto build() {
            return new StationListDto(this);
        }
    }
}
