package com.weather.server.domain.entity;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Station { //? seperate one collection for existing, produced stations?
    @Id
    String id;

    String stationId;
    Boolean visible;
    String geolocationCoordinateN;
    String geolocationCoordinateE;


    public Boolean getVisible() {
        return visible;
    }

    public String getId() {
        return id;
    }

    public String getStationId() {
        return stationId;
    }

    public Boolean getVisibility() {
        return visible;
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

    public void setVisibility(Boolean visibility) {
        this.visible = visibility;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id='" + id + '\'' +
                ", stationId='" + stationId + '\'' +
                ", visibility='" + visible + '\'' +
                ", geolocationCoordinateN='" + geolocationCoordinateN + '\'' +
                ", geolocationCoordinateE='" + geolocationCoordinateE + '\'' +
                '}';
    }
}
