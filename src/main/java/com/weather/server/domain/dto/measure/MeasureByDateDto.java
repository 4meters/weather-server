package com.weather.server.domain.dto.measure;


public class MeasureByDateDto {
    String apiKey;
    String stationId;
    String dateFrom;
    String dateTo;


    public MeasureByDateDto(String apiKey, String stationId, String dateFrom, String dateTo) {
        this.apiKey = apiKey;
        this.stationId = stationId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
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

    public String getApiKey() {
        return apiKey;
    }


}
