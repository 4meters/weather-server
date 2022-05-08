package com.weather.server.domain.dto.user;

public class UserPasswordChangeDto {
    private String token;
    private String oldPassword;
    private String newPassword;

    public UserPasswordChangeDto() {
    }

    public UserPasswordChangeDto(Builder builder) {
        this.token = builder.token;
        this.oldPassword = builder.oldPassword;
        this.newPassword = builder.newPassword;
    }

    public String getToken() {
        return token;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public static final class Builder {
        private String token;
        private String oldPassword;
        private String newPassword;

        private Builder() {
        }

        public static Builder anUserPasswordChangeDto() {
            return new Builder();
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder oldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
            return this;
        }

        public Builder newPassword(String newPassword) {
            this.newPassword = newPassword;
            return this;
        }

        public UserPasswordChangeDto build() {
            return new UserPasswordChangeDto(this);
        }
    }
}
