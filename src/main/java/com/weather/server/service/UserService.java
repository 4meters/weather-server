package com.weather.server.service;

import com.weather.server.domain.dto.user.UserApiKeyDto;
import com.weather.server.domain.dto.user.UserAssignStationDto;
import com.weather.server.domain.dto.user.UserLoginDto;
import com.weather.server.domain.dto.user.UserLoginTokenDto;

public interface UserService {
    boolean createUser(UserLoginDto userLoginDto);
    UserLoginTokenDto loginUser(UserLoginDto userLoginDto);
    boolean generateApiKey(UserLoginTokenDto userLoginTokenDto);
    UserApiKeyDto readApiKey(String token);
    boolean checkToken(String token);

    boolean assignStationId(UserAssignStationDto userAssignStationDto);

    boolean verifyEmail();
    //TODO verify password
    //TODO reset password?
}
