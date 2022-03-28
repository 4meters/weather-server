package com.weather.server.domain.dto.user;

public class UserLoginDto {
    private String email;
    private String password;

    public UserLoginDto() {
    }

    public UserLoginDto(Builder builder) {
        this.email = builder.email;
        this.password = builder.password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public static final class Builder {
        private String email;
        private String password;

        public Builder() {
        }

        public Builder email(String email) {
            this.email = email;
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
