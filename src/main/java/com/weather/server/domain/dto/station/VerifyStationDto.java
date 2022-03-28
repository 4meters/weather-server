package com.weather.server.domain.dto.station;

public class VerifyStationDto {
    private String stationId;
    private String token;

    public VerifyStationDto(String stationId, String token) {
        this.stationId = stationId;
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
