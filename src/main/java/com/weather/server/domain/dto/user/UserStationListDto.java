package com.weather.server.domain.dto.user;

import com.weather.server.domain.dto.station.StationListDto;
import com.weather.server.domain.entity.Station;

import java.util.List;

public class UserStationListDto {
    private List<Station> myStationList;
    private List<Station> bookmarkStationList;

    public UserStationListDto() {
    }

    public List<Station> getMyStationList() {
        return myStationList;
    }

    public List<Station> getBookmarkStationList() {
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
        private List<Station> myStationList;
        private List<Station> bookmarkStationList;

        public Builder() {
        }

        public static Builder anUserStationListDto() {
            return new Builder();
        }

        public Builder myStationList(List<Station> myStationList) {
            this.myStationList = myStationList;
            return this;
        }

        public Builder bookmarkStationList(List<Station> bookmarkStationList) {
            this.bookmarkStationList = bookmarkStationList;
            return this;
        }

        public UserStationListDto build() {
            UserStationListDto userStationListDto = new UserStationListDto();
            userStationListDto.bookmarkStationList = this.bookmarkStationList;
            userStationListDto.myStationList = this.myStationList;
            return userStationListDto;
        }
    }
}
