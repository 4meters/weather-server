package com.weather.server.domain.dto.chart;

public class ChartPM10Dto {
    private String date;
    private Float pm10;

    public ChartPM10Dto() {
    }

    private ChartPM10Dto(Builder builder) {
        this.date = builder.date;
        this.pm10 = builder.pm10;
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


    public static final class Builder {
        private String date;
        private Float pm10;

        public Builder() {
        }

        public static Builder aChartPM10Dto() {
            return new Builder();
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public Builder pm10(Float pm10) {
            this.pm10 = pm10;
            return this;
        }

        public ChartPM10Dto build() {
            return new ChartPM10Dto(this);
        }
    }
}

