package com.weather.server.domain.dto.chart;

import java.util.Date;

public class ChartTempDto {
    private String date;
    private Float temp;


    public ChartTempDto(String date, Float temp) {
        this.date = date;
        this.temp = temp;
    }

    public String getDate() {
        return date;
    }

    public Float getTemp() {
        return temp;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }
}

