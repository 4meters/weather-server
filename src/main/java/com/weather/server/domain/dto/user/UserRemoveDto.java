package com.weather.server.domain.dto.user;

public class UserRemoveDto {
    private String password;
    private String token;

    public UserRemoveDto() {
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public UserRemoveDto(Builder builder) {
        this.password = builder.password;
        this.token = builder.token;
    }

    public static final class Builder {
        private String password;
        private String token;

        private Builder() {
        }

        public static Builder anUserRemoveDto() {
            return new Builder();
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public UserRemoveDto build() {
            return new UserRemoveDto(this);
        }
    }
}
