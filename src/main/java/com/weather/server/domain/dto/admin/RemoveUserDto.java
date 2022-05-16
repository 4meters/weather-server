package com.weather.server.domain.dto.admin;

public class RemoveUserDto {
    private String token;
    private String userId;

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public RemoveUserDto() {
    }

    public RemoveUserDto(Builder builder) {
        this.token = builder.token;
        this.userId = builder.userId;
    }


    public static final class Builder {
        private String token;
        private String userId;

        private Builder() {
        }

        public static Builder aRemoveUserDto() {
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

        public RemoveUserDto build() {
            return new RemoveUserDto(this);
        }
    }
}
