package com.weather.server.domain.dto.station;

public class StationDto {
    private String stationId;
    private Boolean visible;
    private String lat;
    private String lng;
    private String stationName;
    private Boolean isActive;
    private String measureInterval;


    public StationDto() {
    }

    private StationDto(Builder builder) {
        this.stationId = builder.stationId;
        this.visible = builder.visible;
        this.lat = builder.lat;
        this.lng = builder.lng;
        this.stationName = builder.stationName;
        this.isActive = builder.isActive;
        this.measureInterval = builder.measureInterval;
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

    public static final class Builder {
        private String stationId;
        private Boolean visible;
        private String lat;
        private String lng;
        private String stationName;
        private Boolean isActive;
        private Boolean isAssigned;
        private String measureInterval;

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

        public StationDto build() {
            return new StationDto(this);
        }
    }
}
