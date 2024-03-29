package com.weather.server.controller;

import com.weather.server.domain.dto.admin.RemoveStationDto;
import com.weather.server.domain.dto.user.UserBookmarkStation;
import com.weather.server.domain.dto.user.*;
import com.weather.server.service.StationService;
import com.weather.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/api/user")
public class UserApiController {

    private UserService userService;
    private StationService stationService;

    @Autowired
    public UserApiController(UserService userService, StationService stationService) {
        this.userService = userService;
        this.stationService = stationService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> createUser(@RequestBody UserLoginDto userLoginDto){
        boolean isCreated=userService.createUser(userLoginDto);
        return isCreated?
                new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDto userLoginDto){
        UserLoginTokenDto userLoginSuccessDto=userService.loginUser(userLoginDto);
        return userLoginSuccessDto != null ? new ResponseEntity<UserLoginTokenDto>(userLoginSuccessDto, HttpStatus.CREATED)
                : new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value="/change-password")
    public ResponseEntity<?> changePassword(@RequestBody UserPasswordChangeDto userPasswordChangeDto){
        return userService.changePassword(userPasswordChangeDto) ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value="/remove-user")
    public ResponseEntity<?> removeUser(@RequestBody UserRemoveDto userRemoveDto){
        return userService.removeUser(userRemoveDto) ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value="/get-user-stationlist/{token}")
    public ResponseEntity<UserStationListDto> getUserStationList(@PathVariable String token){
        UserStationListDto userStationListDto = stationService.getUserStationList(token);
        return userStationListDto!=null ? new ResponseEntity<>(userStationListDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value="/get-user-mystationlist-details/{token}")
    public ResponseEntity<UserMyStationListDetailsDto> getUserMyStationListDetails(@PathVariable String token){
        UserMyStationListDetailsDto userMyStationListDetailsDto = stationService.getUserMyStationListDetails(token);
        return userMyStationListDetailsDto!=null ? new ResponseEntity<>(userMyStationListDetailsDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value="/get-user-bookmarkstationlist-details/{token}")
    public ResponseEntity<UserBookmarkStationListDetailsDto> getUserBookmarkStationListDetails(@PathVariable String token){
        UserBookmarkStationListDetailsDto userBookmarkStationListDetailsDto = stationService.getUserBookmarkStationListDetails(token);
        return userBookmarkStationListDetailsDto!=null ? new ResponseEntity<>(userBookmarkStationListDetailsDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value="/add-bookmark")
    public ResponseEntity<?> addBookmark(@RequestBody UserBookmarkStation userBookmarkStation){
        return userService.addBookmark(userBookmarkStation) ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value="/remove-bookmark")
    public ResponseEntity<?> removeBookmark(@RequestBody UserBookmarkStation userBookmarkStation){

        return userService.removeBookmark(userBookmarkStation) ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
