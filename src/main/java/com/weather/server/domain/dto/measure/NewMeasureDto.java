package com.weather.server.domain.dto.measure;

public class NewMeasureDto {
    private String apiKey;
    private String stationId;
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
        stationId = builder.stationId;
        date = builder.date;
        temp = builder.temp;
        humidity = builder.humidity;
        pressure = builder.pressure;
        pm25 = builder.pm25;
        pm10 = builder.pm10;
        pm25Corr = builder.pm25Corr;
    }

    @Override
    public String toString() {
        return "NewMeasureDto{" +
                "apiKey='" + apiKey + '\'' +
                ", stationId='" + stationId + '\'' +
                ", date='" + date + '\'' +
                ", temp='" + temp + '\'' +
                ", humidity='" + humidity + '\'' +
                ", pressure='" + pressure + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", pm10='" + pm10 + '\'' +
                ", pm25Corr='" + pm25Corr + '\'' +
                '}';
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getStationId() {
        return stationId;
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
        private String stationId;
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

        public Builder stationId(String stationId) {
            this.stationId = stationId;
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

        @Override
        public String toString() {
            return "Builder{" +
                    "apiKey='" + apiKey + '\'' +
                    ", stationId='" + stationId + '\'' +
                    ", date='" + date + '\'' +
                    ", temp='" + temp + '\'' +
                    ", humidity='" + humidity + '\'' +
                    ", pressure='" + pressure + '\'' +
                    ", pm25='" + pm25 + '\'' +
                    ", pm10='" + pm10 + '\'' +
                    ", pm25Corr='" + pm25Corr + '\'' +
                    '}';
        }
    }
}
