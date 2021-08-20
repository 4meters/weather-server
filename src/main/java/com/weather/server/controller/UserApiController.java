package com.weather.server.controller;

import com.weather.server.domain.dto.UserLoginDto;
import com.weather.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/api")
public class UserApiController {



    private UserService userService;

    @Autowired
    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/user/create")
    public ResponseEntity<?> createUser(@RequestBody UserLoginDto userLoginDto){
        boolean isCreated=userService.createUser(userLoginDto);
        return isCreated? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
    @PostMapping(value = "/user/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDto userLoginDto){
        UserLoginSuccessDto userLoginSuccessDto=userService.loginUser(userLoginDto);
        if(userLoginSuccessDto!=null){
            return new ResponseEntity<UserLoginSuccessDto>(userLoginSuccessDto, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<UserLoginSuccessDto>((UserLoginSuccessDto) null, HttpStatus.BAD_REQUEST);
        }
    }

    //for testing
/*@GetMapping(value = "/user/test/readall")
public ResponseEntity<UserListDto> getList(){
    List<User> u=userService.readAll();

    UserListDto u2= new UserListDto(u);
    return new ResponseEntity<UserListDto>(u2, HttpStatus.OK);
}*/

}
