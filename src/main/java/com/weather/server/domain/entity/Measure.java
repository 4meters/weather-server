package com.weather.server.domain.entity;


import org.springframework.data.annotation.Id;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Measure {

    @Id
    public String id;

    public String userId;
    public Date date;
    public String temp;
    public String humidity;
    public String pressure;
    public String pm25;
    public String pm25Corr;
    public String pm10;


    public Measure() {
    }

    public Measure(String userId, Date date, String temp, String humidity, String pressure, String pm25,
                   String pm10, String pm25Corr) {
        this.userId = userId;
        this.date = date;
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.pm25 = pm25;
        this.pm25Corr = pm25Corr;
        this.pm10 = pm10;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public void setPm25Corr(String pm25Corr) {
        this.pm25Corr = pm25Corr;
    }
    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }




    @Override
    public String toString() {
        SimpleDateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String isoDate = sdfISO.format(date);

        return "Measure{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", temp='" + temp + '\'' +
                ", pressure='" + pressure + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", pm25Corr='" + pm25Corr + '\'' +
                ", pm10='" + pm10 + '\'' +
                ", date='" + isoDate + '\'' +
                '}';
    }
}
