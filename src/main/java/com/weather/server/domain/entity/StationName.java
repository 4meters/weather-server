package com.weather.server.domain.entity;

import org.springframework.data.annotation.Id;

public class StationName {

    @Id
    String id;

    private String publicStationName;
    private String userStationName;
    private String stationId;
}
