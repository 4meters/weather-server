package com.weather.server.domain.dto.chart;

public class ChartPM10Dto {
    private String date;
    private Float pm10;


    public ChartPM10Dto(String date, Float temp) {
        this.date = date;
        this.pm10 = temp;
    }

    public String getDate() {
        return date;
    }

    public Float getPm10() {
        return pm10;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPm10(Float pm10) {
        this.pm10 = pm10;
    }
}

