package com.weather.server.domain.dto.admin;

public class UserNewPassword {
    private String newPassword;

    public UserNewPassword() {
    }

    public UserNewPassword(Builder builder) {
        this.newPassword = builder.newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }


    public static final class Builder {
        private String newPassword;

        public Builder() {
        }

        public static Builder anUserNewPassword() {
            return new Builder();
        }

        public Builder newPassword(String newPassword) {
            this.newPassword = newPassword;
            return this;
        }

        public UserNewPassword build() {
            return new UserNewPassword(this);
        }
    }
}
