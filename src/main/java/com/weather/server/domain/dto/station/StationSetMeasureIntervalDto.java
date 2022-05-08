package com.weather.server.domain.dto.station;

public class StationSetMeasureIntervalDto {
    private String stationId;
    private String token;
    private String measureInterval;

    public StationSetMeasureIntervalDto() {
    }

    public String getStationId() {
        return stationId;
    }

    public String getToken() {
        return token;
    }

    public String getMeasureInterval() {
        return measureInterval;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setMeasureInterval(String measureInterval) {
        this.measureInterval = measureInterval;
    }

    private StationSetMeasureIntervalDto(Builder builder) {
        this.stationId = builder.stationId;
        this.token = builder.token;
        this.measureInterval = builder.measureInterval;
    }

    public static final class Builder {
        private String stationId;
        private String token;
        private String measureInterval;

        private Builder() {
        }

        public static Builder aStationSetMeasureIntervalDto() {
            return new Builder();
        }

        public Builder stationId(String stationId) {
            this.stationId = stationId;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder measureInterval(String measureInterval) {
            this.measureInterval = measureInterval;
            return this;
        }

        public StationSetMeasureIntervalDto build() {
            return new StationSetMeasureIntervalDto(this);
        }
    }
}
