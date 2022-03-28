package com.weather.server.domain.dto.user;

public class UserLoginTokenDto {
    private String token;


    public UserLoginTokenDto() {
    }

    public UserLoginTokenDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
