package com.weather.server.domain.entity;

import org.springframework.data.annotation.Id;

public class User {
    @Id
    String id;

    String userId;
    String login;
    String password;
    String token;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    String apiKey;

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }



    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + login + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
