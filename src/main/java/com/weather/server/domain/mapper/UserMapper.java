package com.weather.server.domain.mapper;

import com.weather.server.domain.dto.user.UserDto;
import com.weather.server.domain.dto.user.UserListDto;
import com.weather.server.domain.dto.user.UserLoginDto;
import com.weather.server.domain.entity.User;
import com.weather.server.domain.model.PasswordHasher;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public User mapToEntity(UserLoginDto userLoginDto) throws InvalidKeySpecException, NoSuchAlgorithmException {
        User user = new User();
        user.setLogin(userLoginDto.getLogin());
        user.setPassword(PasswordHasher.hashNewPassword(userLoginDto.getPassword()));
        return user;
    }

    public UserListDto mapToDto(List<User> userList){
        List<UserDto> userDtoList = new ArrayList<>();
        for(User user : userList){
            UserDto userDto = new UserDto.Builder().
                    login(user.getLogin()).
                    userId(user.getUserId()).
                    build();
            userDtoList.add(userDto);
        }
        return new UserListDto.Builder().userList(userDtoList).build();
    }
}
