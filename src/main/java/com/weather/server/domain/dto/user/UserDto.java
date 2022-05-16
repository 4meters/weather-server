package com.weather.server.domain.dto.user;

public class UserDto { //for admin get all users
    private String login;
    private String userId;

    public UserDto() {
    }

    public UserDto(Builder builder) {
        this.login = builder.login;
        this.userId = builder.userId;
    }

    public String getLogin() {
        return login;
    }

    public String getUserId() {
        return userId;
    }


    public static final class Builder {
        private String login;
        private String userId;

        public Builder() {
        }

        public static Builder anUserDto() {
            return new Builder();
        }

        public Builder login(String login) {
            this.login = login;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }
}
