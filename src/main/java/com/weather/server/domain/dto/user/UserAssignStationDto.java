package com.weather.server.domain.dto.user;

public class UserAssignStationDto {
    private String token;
    private String stationId;
    private String stationIdentifyKey;

    public UserAssignStationDto(String token, String stationId, String stationIdentifyKey) {
        this.token = token;
        this.stationId = stationId;
        this.stationIdentifyKey = stationIdentifyKey;
    }

    public String getToken() {
        return token;
    }

    public String getStationId() {
        return stationId;
    }

    public String getStationIdentifyKey() {
        return stationIdentifyKey;
    }
}

