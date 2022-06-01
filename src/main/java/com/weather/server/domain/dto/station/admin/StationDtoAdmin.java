package com.weather.server.domain.dto.station.admin;

public class StationDtoAdmin {
    private String stationId;
    private Boolean visible;
    private String lat;
    private String lng;
    private String stationName;
    private String stationKey;
    private Boolean isActive;
    private Boolean isAssigned;
    private String measureInterval;


    public StationDtoAdmin() {
    }

    private StationDtoAdmin(Builder builder) {
        this.stationId = builder.stationId;
        this.visible = builder.visible;
        this.lat = builder.lat;
        this.lng = builder.lng;
        this.stationName = builder.stationName;
        this.isActive = builder.isActive;
        this.measureInterval = builder.measureInterval;
        this.isAssigned = builder.isAssigned;
        this.stationKey = builder.stationKey;
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

    public Boolean getActive() {
        return isActive;
    }

    public String getMeasureInterval() {
        return measureInterval;
    }

    public String getStationKey() {
        return stationKey;
    }

    public Boolean getAssigned() {
        return isAssigned;
    }

    public static final class Builder {
        private String stationId;
        private Boolean visible;
        private String lat;
        private String lng;
        private String stationName;
        private Boolean isActive;
        private Boolean isAssigned;
        private String measureInterval;
        private String stationKey;

        public Builder() {
        }

        public static Builder aStationDto() {
            return new Builder();
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

        public Builder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Builder isAssigned(Boolean isAssigned) {
            this.isAssigned = isAssigned;
            return this;
        }

        public Builder measureInterval(String measureInterval) {
            this.measureInterval = measureInterval;
            return this;
        }

        public Builder stationKey(String stationKey) {
            this.stationKey = stationKey;
            return this;
        }

        public StationDtoAdmin build() {
            return new StationDtoAdmin(this);
        }
    }
}
