package com.weather.server.domain.dto.station;

public class StationModeSwitchDto {
    private String stationId;
    private String token;
    private String mode;

    public StationModeSwitchDto() {
    }

    public String getStationId() {
        return stationId;
    }

    public String getToken() {
        return token;
    }

    public String getMode() {
        return mode;
    }

    private StationModeSwitchDto(Builder builder) {
        this.stationId = builder.stationId;
        this.token = builder.token;
        this.mode = builder.mode;
    }

    public static final class Builder {
        private String stationId;
        private String token;
        private String mode;

        private Builder() {
        }

        public static Builder aStationModeSwitchDto() {
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

        public Builder mode(String mode) {
            this.mode = mode;
            return this;
        }

        public StationModeSwitchDto build() {
            return new StationModeSwitchDto(this);
        }
    }
}
