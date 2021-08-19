package com.weather.server.domain.dto;

public class MeasureDto {
    private String apiKey;
    private String timestamp;
    private String temp;
    private String humidity;
    private String pressure;
    private String pm25;
    private String pm10;
    private String pm25Corr;

    public MeasureDto() {
    }

    public MeasureDto(String apiKey, String timestamp, String temp, String humidity, String pressure, String pm25, String pm10, String pm25Corr) {
        this.apiKey = apiKey;
        this.timestamp = timestamp;
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.pm25Corr = pm25Corr;
    }


    public String getApiKey() {
        return apiKey;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getTemp() {
        return temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public String getPm25() {
        return pm25;
    }

    public String getPm10() {
        return pm10;
    }

    public String getPm25Corr() {
        return pm25Corr;
    }
}
