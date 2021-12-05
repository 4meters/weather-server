package com.weather.server.controller;

import com.weather.server.domain.dto.UserApiKeyDto;
import com.weather.server.domain.dto.UserLoginDto;
import com.weather.server.domain.dto.UserLoginTokenDto;
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

    @Autowired
    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser(@RequestBody UserLoginDto userLoginDto){
        boolean isCreated=userService.createUser(userLoginDto);
        return isCreated?
                new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDto userLoginDto){
        UserLoginTokenDto userLoginSuccessDto=userService.loginUser(userLoginDto);
        if(userLoginSuccessDto!=null){
            return new ResponseEntity<>(userLoginSuccessDto, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>((UserLoginTokenDto) null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getApiKey")
    public ResponseEntity<?> getApiKey(@RequestBody UserLoginTokenDto userLoginTokenDto){
        //add return if token valid, user found, but key has to be generated or generate apikey if not generated
        UserApiKeyDto userApiKeyDto=userService.readApiKey(userLoginTokenDto.getToken());
        return userApiKeyDto != null ?
                new ResponseEntity<UserApiKeyDto>(userApiKeyDto, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value= "/generateApiKey")
    public ResponseEntity<?> generateApiKey(@RequestBody UserLoginTokenDto userLoginTokenDto){
        return userService.generateApiKey(userLoginTokenDto) ?
                new ResponseEntity<UserApiKeyDto>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //add new station

    //

    //for testing
/*@GetMapping(value = "/user/test/readall")
public ResponseEntity<UserListDto> getList(){
    List<User> u=userService.readAll();

    UserListDto u2= new UserListDto(u);
    return new ResponseEntity<UserListDto>(u2, HttpStatus.OK);
}*/

}
