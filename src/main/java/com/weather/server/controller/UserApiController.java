package com.weather.server.controller;

import com.weather.server.domain.dto.user.UserLoginDto;
import com.weather.server.domain.dto.user.UserLoginTokenDto;
import com.weather.server.domain.dto.user.UserStationListDto;
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

    @GetMapping(value="/get-user-stationlist/{token}")
    public ResponseEntity<UserStationListDto> getUserStationList(@PathVariable String token){
        UserStationListDto userStationListDto = stationService.getUserStationList(token);
        System.out.println(userStationListDto);
        return userStationListDto!=null ? new ResponseEntity<>(userStationListDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
