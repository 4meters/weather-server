package com.weather.server.domain.dto.station.admin;

import java.util.List;

public class StationListDtoAdmin {
    private List<StationDtoAdmin> stationList;



    private StationListDtoAdmin(Builder builder) {
        this.stationList = builder.stationList;
    }

    public List<StationDtoAdmin> getStationList() {
        return stationList;
    }

    public static final class Builder {
        private List<StationDtoAdmin> stationList;

        public Builder() {
        }

        public Builder stationList(List<StationDtoAdmin> stationList) {
            this.stationList = stationList;
            return this;
        }

        public StationListDtoAdmin build() {
            return new StationListDtoAdmin(this);
        }
    }
}
