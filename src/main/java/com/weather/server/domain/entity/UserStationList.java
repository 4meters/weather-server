package com.weather.server.domain.entity;

import org.springframework.data.annotation.Id;

import java.util.List;

public class UserStationList {
    @Id
    String id;

    private String userId;
    private List<String> myStationList;
    private List<String> bookmarkStationList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getMyStationList() {
        return myStationList;
    }

    public void setMyStationList(List<String> myStationList) {
        this.myStationList = myStationList;
    }

    public List<String> getBookmarkStationList() {
        return bookmarkStationList;
    }

    public void setBookmarkStationList(List<String> bookmarkStationList) {
        this.bookmarkStationList = bookmarkStationList;
    }
}
