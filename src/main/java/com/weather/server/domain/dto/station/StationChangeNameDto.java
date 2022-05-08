package com.weather.server.domain.dto.station;

public class StationChangeNameDto {
    private String token;
    private String stationId;
    private String newStationName;

    public StationChangeNameDto() {
    }

    private StationChangeNameDto(Builder builder) {
        this.token = builder.token;
        this.stationId = builder.stationId;
        this.newStationName = builder.newStationName;
    }

    public String getToken() {
        return token;
    }

    public String getStationId() {
        return stationId;
    }

    public String getNewStationName() {
        return newStationName;
    }


    public static final class Builder {
        private String token;
        private String stationId;
        private String newStationName;

        private Builder() {
        }

        public static Builder aStationChangeNameDto() {
            return new Builder();
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder stationId(String stationId) {
            this.stationId = stationId;
            return this;
        }

        public Builder newStationName(String newStationName) {
            this.newStationName = newStationName;
            return this;
        }

        public StationChangeNameDto build() {
            return new StationChangeNameDto(this);
        }
    }
}
