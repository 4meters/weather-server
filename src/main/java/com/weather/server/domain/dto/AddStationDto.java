package com.weather.server.domain.dto;

public class AddStationDto {
    private String stationId;
    private Boolean visible;
    private String geolocationCoordinateN;
    private String geolocationCoordinateE;

    public AddStationDto(Builder builder) {
        this.stationId = builder.stationId;
        this.visible = builder.visible;
        this.geolocationCoordinateN = builder.geolocationCoordinateN;
        this.geolocationCoordinateE = builder.geolocationCoordinateE;
    }

    public static final class Builder {
        private String stationId;
        private Boolean visible;
        private String geolocationCoordinateN;
        private String geolocationCoordinateE;

        private Builder() {
        }

        public static Builder anAddStationDto() {
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

        public Builder geolocationCoordinateN(String geolocationCoordinateN) {
            this.geolocationCoordinateN = geolocationCoordinateN;
            return this;
        }

        public Builder geolocationCoordinateE(String geolocationCoordinateE) {
            this.geolocationCoordinateE = geolocationCoordinateE;
            return this;
        }

        public AddStationDto build() {
            return new AddStationDto(this);
        }
    }
}
