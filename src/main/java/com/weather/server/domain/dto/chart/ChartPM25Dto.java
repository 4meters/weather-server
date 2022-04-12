package com.weather.server.domain.dto.chart;

public class ChartPM25Dto {
    private String date;
    private Float pm25;


    public ChartPM25Dto(String date, Float temp) {
        this.date = date;
        this.pm25 = temp;
    }

    public String getDate() {
        return date;
    }

    public Float getPm25() {
        return pm25;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPm25(Float pm25) {
        this.pm25 = pm25;
    }
}

