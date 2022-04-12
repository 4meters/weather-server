package com.weather.server.domain.dto.measure;


public class MeasureByDateChartDto {
    String token;
    String stationId;
    String dateFrom;
    String dateTo;
    String timezone;
    String chartValue;
    String chartType;

    public MeasureByDateChartDto(String token, String stationId, String dateFrom, String dateTo, String timezone, String chartValue, String chartType) {
        this.token = token;
        this.stationId = stationId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.timezone = timezone;
        this.chartValue = chartValue;
        this.chartType = chartType;
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


}
