package com.weather.server.service;

import com.weather.server.domain.dto.user.UserBookmarkStation;
import com.weather.server.domain.dto.user.UserLoginDto;
import com.weather.server.domain.dto.user.UserLoginTokenDto;
import com.weather.server.domain.dto.user.UserPasswordChangeDto;

public interface UserService {
    boolean createUser(UserLoginDto userLoginDto);
    UserLoginTokenDto loginUser(UserLoginDto userLoginDto);
    boolean checkToken(String token);

    //boolean assignStationId(UserAssignStationDto userAssignStationDto);

    boolean changePassword(UserPasswordChangeDto userPasswordChangeDto);

    boolean addBookmark(UserBookmarkStation userBookmarkStation);
    boolean removeBookmark(UserBookmarkStation userBookmarkStation);

    //TODO verify password
    //TODO reset password?
}
