package com.weather.server.domain.mapper;

import com.weather.server.domain.dto.UserLoginDto;
import com.weather.server.domain.entity.User;

public class UserMapper {
    public User mapToEntity(UserLoginDto userLoginDto){
        User user = new User();
        user.setEmail(userLoginDto.getEmail());
        user.setPassword(userLoginDto.getPassword());
        return user;
    }
}
