package com.weather.server.domain.dto.measure;


public class MeasureByDateChartDto {
    String token;
    String stationId;
    String dateFrom;
    String dateTo;
    String timezone;
    String chartValue;
    String chartType;

    public MeasureByDateChartDto() {
    }

    private MeasureByDateChartDto(Builder builder) {
        this.token = builder.token;
        this.stationId = builder.stationId;
        this.dateFrom = builder.dateFrom;
        this.dateTo = builder.dateTo;
        this.timezone = builder.timezone;
        this.chartValue = builder.chartValue;
        this.chartType = builder.chartType;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getChartValue() {
        return chartValue;
    }

    public void setChartValue(String chartValue) {
        this.chartValue = chartValue;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public String getStationId() {
        return stationId;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public String getToken() {
        return token;
    }


    public static final class Builder {
        String token;
        String stationId;
        String dateFrom;
        String dateTo;
        String timezone;
        String chartValue;
        String chartType;

        private Builder() {
        }

        public static Builder aMeasureByDateChartDto() {
            return new Builder();
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder stationId(String stationId) {
            this.stationId = stationId;
            return this;
        }

        public Builder dateFrom(String dateFrom) {
            this.dateFrom = dateFrom;
            return this;
        }

        public Builder dateTo(String dateTo) {
            this.dateTo = dateTo;
            return this;
        }

        public Builder timezone(String timezone) {
            this.timezone = timezone;
            return this;
        }

        public Builder chartValue(String chartValue) {
            this.chartValue = chartValue;
            return this;
        }

        public Builder chartType(String chartType) {
            this.chartType = chartType;
            return this;
        }

        public MeasureByDateChartDto build() {
            return new MeasureByDateChartDto(this);
        }
    }
}
