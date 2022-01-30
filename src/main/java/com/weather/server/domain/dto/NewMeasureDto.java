package com.weather.server.domain.dto;

public class NewMeasureDto {
    private String apiKey;
    private String stationID;
    private String date;
    private String temp;
    private String humidity;
    private String pressure;
    private String pm25;
    private String pm10;
    private String pm25Corr;
    //station_id

    public NewMeasureDto() {
    }

    private NewMeasureDto(Builder builder){
        apiKey = builder.apiKey;
        stationID = builder.stationID;
        date = builder.date;
        temp = builder.temp;
        humidity = builder.humidity;
        pressure = builder.pressure;
        pm25 = builder.pm25;
        pm10 = builder.pm10;
        pm25Corr = builder.pm25Corr;
    }


    public String getApiKey() {
        return apiKey;
    }

    public String getStationID() {
        return stationID;
    }

    public String getDate() {
        return date;
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


    public static final class Builder {
        private String apiKey;
        private String stationID;
        private String date;
        private String temp;
        private String humidity;
        private String pressure;
        private String pm25;
        private String pm10;
        private String pm25Corr;

        public Builder() {
        }

        public Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder stationID(String stationID) {
            this.stationID = stationID;
            return this;
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public Builder temp(String temp) {
            this.temp = temp;
            return this;
        }

        public Builder humidity(String humidity) {
            this.humidity = humidity;
            return this;
        }

        public Builder pressure(String pressure){
            this.pressure = pressure;
            return this;
        }

        public Builder pm25(String pm25){
            this.pm25 = pm25;
            return this;
        }

        public Builder pm10(String pm10){
            this.pm10 = pm10;
            return this;
        }

        public Builder pm25Corr(String pm25Corr){
            this.pm25Corr = pm25Corr;
            return this;
        }

        public NewMeasureDto build() {
            return new NewMeasureDto(this);
        }
    }
}
