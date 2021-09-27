package com.weather.server.domain.dto;


public class MeasureByDateDto {
    String apiKey;
    String dateFrom;
    String dateTo;

    public MeasureByDateDto(String apiKey, String dateFrom, String dateTo) {
        this.apiKey = apiKey;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public String getApiKey() {
        return apiKey;
    }
}
