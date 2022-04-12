package com.weather.server.domain.dto.chart;

public class ChartHumidityDto {
    private String date;
    private Float humidity;


    public ChartHumidityDto(String date, Float temp) {
        this.date = date;
        this.humidity = temp;
    }

    public String getDate() {
        return date;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }
}

