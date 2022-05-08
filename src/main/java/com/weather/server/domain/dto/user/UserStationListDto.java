package com.weather.server.domain.dto.user;

import com.weather.server.domain.dto.station.StationDto;
import com.weather.server.domain.dto.station.StationListDto;
import com.weather.server.domain.entity.Station;

import java.util.List;

public class UserStationListDto {
    private List<StationDto> myStationList;
    private List<StationDto> bookmarkStationList;

    public UserStationListDto() {
    }

    public UserStationListDto(Builder builder) {
        this.myStationList = builder.myStationList;
        this.bookmarkStationList = builder.bookmarkStationList;
    }

    public List<StationDto> getMyStationList() {
        return myStationList;
    }

    public List<StationDto> getBookmarkStationList() {
        return bookmarkStationList;
    }

    @Override
    public String toString() {
        return "UserStationListDto{" +
                "myStationList=" + myStationList +
                ", bookmarkStationList=" + bookmarkStationList +
                '}';
    }


    public static final class Builder {
        private List<StationDto> myStationList;
        private List<StationDto> bookmarkStationList;

        public Builder() {
        }

        public static Builder anUserStationListDto() {
            return new Builder();
        }

        public Builder myStationList(List<StationDto> myStationList) {
            this.myStationList = myStationList;
            return this;
        }

        public Builder bookmarkStationList(List<StationDto> bookmarkStationList) {
            this.bookmarkStationList = bookmarkStationList;
            return this;
        }

        public UserStationListDto build() {
            return new UserStationListDto(this);
        }
    }
}
