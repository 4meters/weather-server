package com.weather.server.domain.dto.admin;

public class AddStationToDbDto {
    private String token;
    private String stationId;
    private String stationKey;

    public AddStationToDbDto() {
    }

    public AddStationToDbDto(Builder builder) {
        this.token = builder.token;
        this.stationId = builder.stationId;
        this.stationKey = builder.stationKey;
    }

    public String getToken() {
        return token;
    }

    public String getStationId() {
        return stationId;
    }

    public String getStationKey() {
        return stationKey;
    }

    public static final class Builder {
        private String token;
        private String stationId;
        private String stationKey;

        private Builder() {
        }

        public static Builder anAddStationToDbDto() {
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

        public Builder stationKey(String stationKey) {
            this.stationKey = stationKey;
            return this;
        }

        public AddStationToDbDto build() {
            return new AddStationToDbDto(this);
        }
    }
}
