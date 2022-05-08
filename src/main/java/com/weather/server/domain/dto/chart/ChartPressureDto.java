package com.weather.server.domain.dto.chart;

public class ChartPressureDto {
    private String date;
    private Float pressure;

    public ChartPressureDto() {
    }

    private ChartPressureDto(Builder builder) {
        this.date = builder.date;
        this.pressure = builder.pressure;
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


    public static final class Builder {
        private String date;
        private Float pressure;

        public Builder() {
        }

        public static Builder aChartPressureDto() {
            return new Builder();
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public Builder pressure(Float pressure) {
            this.pressure = pressure;
            return this;
        }

        public ChartPressureDto build() {
            return new ChartPressureDto(this);
        }
    }
}

