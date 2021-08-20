package com.weather.server.service;

import com.weather.server.domain.dto.UserApiKeyDto;
import com.weather.server.domain.dto.UserLoginDto;
import com.weather.server.domain.dto.UserLoginTokenDto;

public interface UserService {
    void createUser(UserLoginDto userLoginDto);
    UserLoginTokenDto loginUser();
    boolean generateApiKey();
    UserApiKeyDto readApiKey(String token);
    boolean checkToken();
}
