package com.weather.server.domain.entity;


import org.springframework.data.annotation.Id;

public class Measure {

    @Id
    public String id;

    public String timestamp;
    public String temp;
    public String pressure;
    public String pm25;
    public String pm10;
    public String pm25Corr;

    public Measure() {
    }

    public Measure(String timestamp, String temp, String pressure, String pm25, String pm10, String pm25Corr) {
        this.timestamp = timestamp;
        this.temp = temp;
        this.pressure = pressure;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.pm25Corr = pm25Corr;
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
