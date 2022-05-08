package com.weather.server.domain.dto.user;

public class UserBookmarkStation {
    private String token;
    private String stationId;

    public UserBookmarkStation() {
    }

    public String getToken() {
        return token;
    }

    public String getStationId() {
        return stationId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public static final class Builder {
        private String token;
        private String stationId;

        private Builder() {
        }

        public static Builder anUserBookmarkStation() {
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

        public UserBookmarkStation build() {
            UserBookmarkStation userBookmarkStation = new UserBookmarkStation();
            userBookmarkStation.token = this.token;
            userBookmarkStation.stationId = this.stationId;
            return userBookmarkStation;
        }
    }
}
