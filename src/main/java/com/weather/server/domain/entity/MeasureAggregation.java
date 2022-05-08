package com.weather.server.domain.entity;


import org.bson.types.Decimal128;
import org.springframework.data.annotation.Id;

import java.text.SimpleDateFormat;

public class MeasureAggregation {

    @Id
    public String _id;

    public String stationId;
    public String date;
    public Decimal128 temp;
    public Decimal128 humidity;
    public Decimal128 pressure;
    public Decimal128 pm25;
    public Decimal128 pm25Corr;
    public Decimal128 pm10;


    public MeasureAggregation() {
    }

    public MeasureAggregation(String stationId, String date, Decimal128 temp, Decimal128 humidity, Decimal128 pressure, Decimal128 pm25,
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


    public void set_id(String _id) {
        this._id = _id;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public void setDate(String date) {
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
                "id='" + _id + '\'' +
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
