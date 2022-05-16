package com.weather.server.domain.dto.user;

import java.util.List;

public class UserListDto {
    private List<UserDto> userList;

    public UserListDto() {
    }

    public UserListDto(Builder builder) {
        this.userList = builder.userList;
    }

    public List<UserDto> getUserList() {
        return userList;
    }

    public static final class Builder {
        private List<UserDto> userList;

        public Builder() {
        }

        public static Builder anUserListDto() {
            return new Builder();
        }

        public Builder userList(List<UserDto> userList) {
            this.userList = userList;
            return this;
        }

        public UserListDto build() {
            return new UserListDto(this);
        }
    }
}
