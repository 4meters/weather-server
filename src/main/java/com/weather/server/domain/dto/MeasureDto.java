package com.weather.server.domain.dto;

import java.util.Date;

public class MeasureDto {
    private String apiKey;
    private String stationID;
    private String date;
    private String temp;
    private String humidity;
    private String pressure;
    private String pm25;
    private String pm10;
    private String pm25Corr;
    //station_id

    public MeasureDto() {
    }

    public MeasureDto(String apiKey, String stationID, String date, String temp, String humidity, String pressure, String pm25, String pm10, String pm25Corr) {
        this.apiKey = apiKey;
        this.stationID = stationID;
        this.date = date;
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.pm25Corr = pm25Corr;
    }


    public String getApiKey() {
        return apiKey;
    }

    public String getStationID() {
        return stationID;
    }

    public String getDate() {
        return date;
    }

    public String getTemp() {
        return temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public String getPm25() {
        return pm25;
    }

    public String getPm10() {
        return pm10;
    }

    public String getPm25Corr() {
        return pm25Corr;
    }
}
