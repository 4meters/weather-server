package com.weather.server.domain.dto.chart;

public class ChartPM25Dto {
    private String date;
    private Float pm25;

    public ChartPM25Dto() {
    }

    private ChartPM25Dto(Builder builder) {
        this.date = builder.date;
        this.pm25 = builder.pm25;
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


    public static final class Builder {
        private String date;
        private Float pm25;

        public Builder() {
        }

        public static Builder aChartPM25Dto() {
            return new Builder();
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public Builder pm25(Float pm25) {
            this.pm25 = pm25;
            return this;
        }

        public ChartPM25Dto build() {
            return new ChartPM25Dto(this);
        }
    }
}

