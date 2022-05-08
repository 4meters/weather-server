package com.weather.server.domain.dto.chart;

import java.util.Date;

public class ChartTempDto {
    private String date;
    private Float temp;

    public ChartTempDto() {
    }

    private ChartTempDto(Builder builder) {
        this.date = builder.date;
        this.temp = builder.temp;
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


    public static final class Builder {
        private String date;
        private Float temp;

        public Builder() {
        }

        public static Builder aChartTempDto() {
            return new Builder();
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public Builder temp(Float temp) {
            this.temp = temp;
            return this;
        }

        public ChartTempDto build() {
            return new ChartTempDto(this);
        }
    }
}

