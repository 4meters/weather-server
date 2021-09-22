package com.weather.server.domain.entity;


import org.springframework.data.annotation.Id;

import java.util.Date;

public class Measure {

    @Id
    public String id;

    public String userId;
    public String timestamp; //TODO change String to date format for sorting
    public String temp;
    public String humidity;
    public String pressure;
    public String pm25;
    public String pm10;
    public String pm25Corr;
    public Date date;

    public Measure() {
    }

    public Measure(String userId, String timestamp, String temp, String humidity, String pressure, String pm25,
                   String pm10, String pm25Corr, Date date) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.pm25Corr = pm25Corr;
        this.date = date;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public void setPm25Corr(String pm25Corr) {
        this.pm25Corr = pm25Corr;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Measure{" +
                "id='" + id + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", temp='" + temp + '\'' +
                ", pressure='" + pressure + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", pm10='" + pm10 + '\'' +
                ", pm25Corr='" + pm25Corr + '\'' +
                '}';
    }
}
