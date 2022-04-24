package com.weather.server.domain.dto.station;

public class StationCurrentModeDto {
    private String stationId;
    private String mode;
    private String measureInterval;

    public StationCurrentModeDto() {
    }

    public StationCurrentModeDto(Builder builder) {
        this.stationId = builder.stationId;
        this.mode = builder.mode;
        this.measureInterval = builder.measureInterval;
    }

    public String getStationId() {
        return stationId;
    }

    public String getMode() {
        return mode;
    }

    public String getMeasureInterval() {
        return measureInterval;
    }

    public static final class Builder {
        private String stationId;
        private String mode;
        private String measureInterval;

        public Builder() {
        }

        public static Builder aStationGetCurrentModeDto() {
            return new Builder();
        }

        public Builder stationId(String stationId) {
            this.stationId = stationId;
            return this;
        }

        public Builder mode(String mode) {
            this.mode = mode;
            return this;
        }

        public Builder measureInterval(String measureInterval) {
            this.measureInterval = measureInterval;
            return this;
        }

        public StationCurrentModeDto build() {
            StationCurrentModeDto stationCurrentModeDto = new StationCurrentModeDto();
            stationCurrentModeDto.stationId = this.stationId;
            stationCurrentModeDto.measureInterval = this.measureInterval;
            stationCurrentModeDto.mode = this.mode;
            return stationCurrentModeDto;
        }
    }
}
