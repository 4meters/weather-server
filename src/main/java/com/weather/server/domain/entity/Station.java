package com.weather.server.domain.entity;

import org.springframework.data.annotation.Id;

public class Station { //? seperate one collection for existing, produced stations?
    @Id
    String id;

    String stationId;
    String stationKey;
    Boolean visible;
    String lat;
    String lng;
    String stationName;
    Boolean isActive;//TODO
    Boolean isAssigned;
    String measureInterval;

    public String getMeasureInterval() {
        return measureInterval;
    }

    public void setMeasureInterval(String measureInterval) {
        this.measureInterval = measureInterval;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setAssigned(Boolean assigned) {
        isAssigned = assigned;
    }

    public Boolean getActive() {
        return isActive;
    }

    public Boolean getAssigned() {
        return isAssigned;
    }

    public String getStationKey() {
        return stationKey;
    }

    public void setStationKey(String stationKey) {
        this.stationKey = stationKey;
    }
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Boolean getVisible() {
        return visible;
    }

    public String getId() {
        return id;
    }

    public String getStationId() {
        return stationId;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    /*public void setVisibility(Boolean visibility) {
        this.visible = visibility;
    }*/

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id='" + id + '\'' +
                ", stationId='" + stationId + '\'' +
                ", visibility='" + visible + '\'' +
                ", geolocationCoordinateN='" + lat + '\'' +
                ", geolocationCoordinateE='" + lng + '\'' +
                '}';
    }
}
