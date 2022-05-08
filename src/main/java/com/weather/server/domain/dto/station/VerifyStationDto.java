package com.weather.server.domain.dto.station;

public class VerifyStationDto {
    private String stationId;
    private String stationKey;

    public String getStationKey() {
        return stationKey;
    }

    public void setStationKey(String stationKey) {
        this.stationKey = stationKey;
    }

    private String token;

    private VerifyStationDto(Builder builder) {
        this.stationId = builder.stationId;
        this.stationKey = builder.stationKey;
        this.token = builder.token;
    }

    public VerifyStationDto() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }


    public static final class Builder {
        private String stationId;
        private String stationKey;
        private String token;

        private Builder() {
        }

        public static Builder aVerifyStationDto() {
            return new Builder();
        }

        public Builder stationId(String stationId) {
            this.stationId = stationId;
            return this;
        }

        public Builder stationKey(String stationKey) {
            this.stationKey = stationKey;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public VerifyStationDto build() {
            return new VerifyStationDto(this);
        }
    }
}
