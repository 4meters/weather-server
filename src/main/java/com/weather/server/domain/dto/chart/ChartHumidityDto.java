package com.weather.server.domain.dto.chart;

public class ChartHumidityDto {
    private String date;
    private Float humidity;

    public ChartHumidityDto() {
    }

    private ChartHumidityDto(Builder builder) {
        this.date = builder.date;
        this.humidity = builder.humidity;
    }

    public String getDate() {
        return date;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }


    public static final class Builder {
        private String date;
        private Float humidity;

        public Builder() {
        }

        public static Builder aChartHumidityDto() {
            return new Builder();
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public Builder humidity(Float humidity) {
            this.humidity = humidity;
            return this;
        }

        public ChartHumidityDto build() {
            return new ChartHumidityDto(this);
        }
    }
}

