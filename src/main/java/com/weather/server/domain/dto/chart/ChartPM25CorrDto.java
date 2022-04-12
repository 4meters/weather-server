package com.weather.server.domain.dto.chart;

public class ChartPM25CorrDto {
    private String date;
    private Float pm25Corr;


    public ChartPM25CorrDto(String date, Float temp) {
        this.date = date;
        this.pm25Corr = temp;
    }

    public String getDate() {
        return date;
    }

    public Float getPm25Corr() {
        return pm25Corr;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPm25Corr(Float pm25Corr) {
        this.pm25Corr = pm25Corr;
    }
}

