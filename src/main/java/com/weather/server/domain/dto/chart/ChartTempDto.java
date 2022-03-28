package com.weather.server.domain.dto.chart;

import java.util.Date;

public class ChartTempDto {
    private Date date;
    private Float temp;


    public ChartTempDto(Date date, Float temp) {
        this.date = date;
        this.temp = temp;
    }

    public Date getDate() {
        return date;
    }

    public Float getTemp() {
        return temp;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }
}

