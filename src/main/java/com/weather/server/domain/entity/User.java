package com.weather.server.domain.entity;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class User {
    @Id
    String id;

    String userId;
    String email;
    String password;
    String token;
    String apiKey;
    List<String> stationIDList;

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public String getApiKey() {
        return apiKey;
    }

    public List<String> getStationIDList() {
        return stationIDList;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setStationIDList(List<String> stationIDList) {
        this.stationIDList = stationIDList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", apiKey='" + apiKey + '\'' +
                '}';
    }
}
