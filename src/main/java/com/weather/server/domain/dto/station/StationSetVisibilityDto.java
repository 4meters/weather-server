package com.weather.server.domain.dto.station;

public class StationSetVisibilityDto {
    private String token;
    private String visibility;
    private String stationId;

    public StationSetVisibilityDto() {
    }

    public String getToken() {
        return token;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getStationId() {
        return stationId;
    }

    private StationSetVisibilityDto(Builder builder) {
        this.token = builder.token;
        this.visibility = builder.visibility;
        this.stationId = builder.stationId;
    }


    public static final class Builder {
        private String token;
        private String visibility;
        private String stationId;

        private Builder() {
        }

        public static Builder aStationSetVisibilityDto() {
            return new Builder();
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder visibility(String visibility) {
            this.visibility = visibility;
            return this;
        }

        public Builder stationId(String stationId) {
            this.stationId = stationId;
            return this;
        }

        public StationSetVisibilityDto build() {
            return new StationSetVisibilityDto(this);
        }
    }
}
