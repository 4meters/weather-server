package com.weather.server.domain.mapper;

import com.weather.server.domain.dto.user.UserLoginDto;
import com.weather.server.domain.entity.User;
import com.weather.server.domain.model.PasswordHasher;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Component
public class UserMapper {
    public User mapToEntity(UserLoginDto userLoginDto) throws InvalidKeySpecException, NoSuchAlgorithmException {
        User user = new User();
        user.setLogin(userLoginDto.getLogin());
        user.setPassword(PasswordHasher.hashNewPassword(userLoginDto.getPassword()));
        return user;
    }
}
