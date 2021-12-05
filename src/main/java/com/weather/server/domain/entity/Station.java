package com.weather.server.domain.entity;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Station { //? seperate one collection for existing, produced stations?
    @Id
    String id;

    String stationId;
    String visibility;
    String geolocationCoordinateN;
    String geolocationCoordinateE;

    public String getId() {
        return id;
    }

    public String getStationId() {
        return stationId;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getGeolocationCoordinateN() {
        return geolocationCoordinateN;
    }

    public String getGeolocationCoordinateE() {
        return geolocationCoordinateE;
    }

    public void setGeolocationCoordinateN(String geolocationCoordinateN) {
        this.geolocationCoordinateN = geolocationCoordinateN;
    }

    public void setGeolocationCoordinateE(String geolocationCoordinateE) {
        this.geolocationCoordinateE = geolocationCoordinateE;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }


    @Override
    public String toString() {
        return "Station{" +
                "id='" + id + '\'' +
                ", stationId='" + stationId + '\'' +
                ", visibility='" + visibility + '\'' +
                ", geolocationCoordinateN='" + geolocationCoordinateN + '\'' +
                ", geolocationCoordinateE='" + geolocationCoordinateE + '\'' +
                '}';
    }
}
