package com.weather.server.domain.dto.admin;

public class RemoveStationDto {
    private String token;
    private String stationId;
    private Boolean removeMeasures;

    public RemoveStationDto() {
    }

    public RemoveStationDto(Builder builder) {
        this.token = builder.token;
        this.stationId = builder.stationId;
        this.removeMeasures = builder.removeMeasures;
    }

    public String getToken() {
        return token;
    }

    public String getStationId() {
        return stationId;
    }

    public Boolean getRemoveMeasures() {
        return removeMeasures;
    }

    public static final class Builder {
        private String token;
        private String stationId;
        private Boolean removeMeasures;

        private Builder() {
        }

        public static Builder aRemoveStationDto() {
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

        public Builder removeMeasures(Boolean removeMeasures) {
            this.removeMeasures = removeMeasures;
            return this;
        }

        public RemoveStationDto build() {
            return new RemoveStationDto(this);
        }
    }
}
