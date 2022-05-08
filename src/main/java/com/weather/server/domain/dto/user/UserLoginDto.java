package com.weather.server.domain.dto.user;

public class UserLoginDto {
    private String login;
    private String password;

    public UserLoginDto() {
    }

    public UserLoginDto(Builder builder) {
        this.login = builder.login;
        this.password = builder.password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public static final class Builder {
        private String login;
        private String password;

        public Builder() {
        }

        public Builder login(String login) {
            this.login = login;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }



        public UserLoginDto build() {
            return new UserLoginDto(this);
        }
    }
}
