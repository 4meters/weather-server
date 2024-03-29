package com.weather.server.domain.dto.station;

public class AddStationDto {

    private String token;
    private String stationId;
    private Boolean visible;
    private String lat;//TODO check if string?
    private String lng;
    private String stationName;


    public AddStationDto() {
    }

    private AddStationDto(Builder builder) {
        this.token = builder.token;
        this.stationId = builder.stationId;
        this.visible = builder.visible;
        this.lat = builder.lat;
        this.lng = builder.lng;
        this.stationName = builder.stationName;
    }

    public String getToken() {
        return token;
    }

    public String getStationId() {
        return stationId;
    }

    public Boolean getVisible() {
        return visible;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getStationName() {
        return stationName;
    }


    public static final class Builder {
        private String token;
        private String stationId;
        private Boolean visible;
        private String lat;//TODO check if string?
        private String lng;
        private String stationName;

        private Builder() {
        }

        public static Builder anAddStationDto() {
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

        public Builder visible(Boolean visible) {
            this.visible = visible;
            return this;
        }

        public Builder lat(String lat) {
            this.lat = lat;
            return this;
        }

        public Builder lng(String lng) {
            this.lng = lng;
            return this;
        }

        public Builder stationName(String stationName) {
            this.stationName = stationName;
            return this;
        }

        public AddStationDto build() {
            return new AddStationDto(this);
        }
    }
}
