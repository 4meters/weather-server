package com.weather.server.service;

import com.weather.server.domain.dto.admin.RemoveStationDto;
import com.weather.server.domain.dto.user.*;
import org.springframework.http.ResponseEntity;

public interface UserService {
    boolean createUser(UserLoginDto userLoginDto);
    UserLoginTokenDto loginUser(UserLoginDto userLoginDto);
    boolean checkToken(String token);

    boolean changePassword(UserPasswordChangeDto userPasswordChangeDto);

    boolean addBookmark(UserBookmarkStation userBookmarkStation);
    boolean removeBookmark(UserBookmarkStation userBookmarkStation);

    ResponseEntity<?> removeStation(RemoveStationDto removeStationDto);

    boolean removeUser(UserRemoveDto userRemoveDto);

    boolean validateToken(String token);

}
