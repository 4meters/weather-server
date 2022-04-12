package com.weather.server.domain.dto.measure;


public class MeasureByDateDto {
    String token;
    String stationId;
    String dateFrom;
    String dateTo;


    public MeasureByDateDto(String apiKey, String stationId, String dateFrom, String dateTo) {
        this.token = apiKey;
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

    public String getToken() {
        return token;
    }


}
