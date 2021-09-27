package com.weather.server.domain.dto;

public class UserApiKeyDto {
    String apiKey;

    public UserApiKeyDto(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }
}
