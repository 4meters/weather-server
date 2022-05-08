package com.weather.server.domain.dto.station;

public class StationNameDto {
    private String stationName;

    public StationNameDto() {
    }

    private StationNameDto(Builder builder) {
        this.stationName = builder.stationName;
    }

    public String getStationName() {
        return stationName;
    }

    public static final class Builder {
        private String stationName;

        public Builder() {
        }

        public Builder stationName(String stationName) {
            this.stationName = stationName;
            return this;
        }

        public StationNameDto build() {
            return new StationNameDto(this);
        }
    }
}
