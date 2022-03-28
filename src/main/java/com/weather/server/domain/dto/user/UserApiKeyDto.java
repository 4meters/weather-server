package com.weather.server.domain.dto.user;

public class UserApiKeyDto {
    String apiKey;

    private UserApiKeyDto(Builder builder) {
        apiKey = builder.apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public static final class Builder {
        private String apiKey;

        public Builder() {
        }

        public Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public UserApiKeyDto build() {
            return new UserApiKeyDto(this);
        }
    }
}
