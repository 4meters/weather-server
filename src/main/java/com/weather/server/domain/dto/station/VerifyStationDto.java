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

    public VerifyStationDto(String stationId, String stationKey, String token) {
        this.stationId = stationId;
        this.stationKey = stationKey;
        this.token = token;
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
}
