package com.weather.server.domain.entity;


import org.bson.types.Decimal128;
import org.springframework.data.annotation.Id;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Measure {

    @Id
    public String id;

    //public String userId;
    public String stationId;
    public Date date;
    public Decimal128 temp;
    public Decimal128 humidity;
    public Decimal128 pressure;
    public Decimal128 pm25;
    public Decimal128 pm25Corr;
    public Decimal128 pm10;


    public Measure() {
    }

    public Measure(String stationId, Date date, Decimal128 temp, Decimal128 humidity, Decimal128 pressure, Decimal128 pm25,
                   Decimal128 pm10, Decimal128 pm25Corr) {
        this.stationId = stationId;
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

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTemp(Decimal128 temp) {
        this.temp = temp;
    }

    public void setHumidity(Decimal128 humidity) {
        this.humidity = humidity;
    }

    public void setPressure(Decimal128 pressure) {
        this.pressure = pressure;
    }

    public void setPm25(Decimal128 pm25) {
        this.pm25 = pm25;
    }

    public void setPm25Corr(Decimal128 pm25Corr) {
        this.pm25Corr = pm25Corr;
    }
    public void setPm10(Decimal128 pm10) {
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
