package com.weather.server.domain.dto.admin;

public class ResetUserPasswordDto {
    private String token;
    private String userId;

    public ResetUserPasswordDto() {
    }

    public ResetUserPasswordDto(Builder builder) {
        this.token = builder.token;
        this.userId = builder.userId;
    }

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }


    public static final class Builder {
        private String token;
        private String userId;

        private Builder() {
        }

        public static Builder aResetUserPasswordDto() {
            return new Builder();
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public ResetUserPasswordDto build() {
            ResetUserPasswordDto resetUserPasswordDto = new ResetUserPasswordDto();
            resetUserPasswordDto.token = this.token;
            resetUserPasswordDto.userId = this.userId;
            return resetUserPasswordDto;
        }
    }
}
