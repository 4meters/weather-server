package com.weather.server.service;

import com.weather.server.domain.dto.UserApiKeyDto;
import com.weather.server.domain.dto.UserLoginDto;
import com.weather.server.domain.dto.UserLoginTokenDto;

public interface UserService {
    boolean createUser(UserLoginDto userLoginDto);
    UserLoginTokenDto loginUser(UserLoginDto userLoginDto);
    boolean generateApiKey(UserLoginTokenDto userLoginTokenDto);
    UserApiKeyDto readApiKey(String token);
    boolean checkToken(String token);

    boolean verifyEmail();
}
