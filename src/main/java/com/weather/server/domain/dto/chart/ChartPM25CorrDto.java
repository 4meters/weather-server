package com.weather.server.domain.dto.chart;

public class ChartPM25CorrDto {
    private String date;
    private Float pm25Corr;

    public ChartPM25CorrDto() {
    }

    private ChartPM25CorrDto(Builder builder) {
        this.date = builder.date;
        this.pm25Corr = builder.pm25Corr;
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


    public static final class Builder {
        private String date;
        private Float pm25Corr;

        public Builder() {
        }

        public static Builder aChartPM25CorrDto() {
            return new Builder();
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public Builder pm25Corr(Float pm25Corr) {
            this.pm25Corr = pm25Corr;
            return this;
        }

        public ChartPM25CorrDto build() {
            return new ChartPM25CorrDto(this);
        }
    }
}

