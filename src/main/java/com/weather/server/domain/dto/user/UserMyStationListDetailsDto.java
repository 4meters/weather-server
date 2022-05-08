package com.weather.server.domain.dto.user;

import com.weather.server.domain.entity.Measure;
import com.weather.server.domain.entity.Station;
import com.weather.server.domain.entity.User;

import java.util.HashMap;
import java.util.List;

public class UserMyStationListDetailsDto {
    private List<Station> stationList;
    private HashMap<String, Measure> measureList;


    public List<Station> getStationList() {
        return stationList;
    }

    public HashMap<String, Measure> getMeasureList() {
        return measureList;
    }

    public UserMyStationListDetailsDto() {
    }

    private UserMyStationListDetailsDto(Builder builder) {
        this.stationList = builder.stationList;
        this.measureList = builder.measureList;
    }

    public static final class Builder {
        private List<Station> stationList;
        private HashMap<String, Measure> measureList;

        public Builder() {
        }

        public static Builder anUserMyStationListDetailsDto() {
            return new Builder();
        }

        public Builder stationList(List<Station> stationList) {
            this.stationList = stationList;
            return this;
        }

        public Builder measureList(HashMap<String, Measure> measureList) {
            this.measureList = measureList;
            return this;
        }

        public UserMyStationListDetailsDto build() {
            return new UserMyStationListDetailsDto(this);
        }
    }
}