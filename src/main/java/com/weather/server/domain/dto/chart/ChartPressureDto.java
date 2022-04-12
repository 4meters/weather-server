package com.weather.server.domain.dto.chart;

public class ChartPressureDto {
    private String date;
    private Float pressure;


    public ChartPressureDto(String date, Float temp) {
        this.date = date;
        this.pressure = temp;
    }

    public String getDate() {
        return date;
    }

    public Float getPressure() {
        return pressure;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }
}

