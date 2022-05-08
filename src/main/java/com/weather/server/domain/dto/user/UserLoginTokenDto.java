package com.weather.server.domain.dto.user;

public class UserLoginTokenDto {
    private String token;


    public UserLoginTokenDto() {
    }

    private UserLoginTokenDto(Builder builder) {
        this.token = builder.token;
    }

    public String getToken() {
        return token;
    }


    public static final class Builder {
        private String token;

        public Builder() {
        }

        public static Builder anUserLoginTokenDto() {
            return new Builder();
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public UserLoginTokenDto build() {
            return new UserLoginTokenDto(this);
        }
    }
}
