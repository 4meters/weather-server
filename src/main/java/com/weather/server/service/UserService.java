package com.weather.server.service;

import com.weather.server.domain.dto.user.UserLoginDto;
import com.weather.server.domain.dto.user.UserLoginTokenDto;

public interface UserService {
    boolean createUser(UserLoginDto userLoginDto);
    UserLoginTokenDto loginUser(UserLoginDto userLoginDto);
    boolean checkToken(String token);

    //boolean assignStationId(UserAssignStationDto userAssignStationDto);

    boolean verifyEmail();
    //TODO verify password
    //TODO reset password?
}
