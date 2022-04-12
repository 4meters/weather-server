package com.weather.server.service.impl;

import com.weather.server.domain.dto.user.UserLoginDto;
import com.weather.server.domain.dto.user.UserLoginTokenDto;
import com.weather.server.domain.entity.User;
import com.weather.server.domain.mapper.UserMapper;
import com.weather.server.domain.model.TokenGenerator;
import com.weather.server.domain.model.UserIdGenerator;
import com.weather.server.domain.repository.StationRepository;
import com.weather.server.domain.repository.UserRepository;
import com.weather.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final StationRepository stationRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, StationRepository stationRepository) {
        this.userRepository=userRepository;
        this.stationRepository=stationRepository;
    }

    @Override
    public boolean createUser(UserLoginDto userLoginDto) {
        //verify email
        //verify password length
        //check if user exists
        //generate password hash
        //
        //or password is hash generated by client
        //verify if send password is hash
        //
        if(userRepository.findByLogin(userLoginDto.getLogin())==null){
            User user = new UserMapper().mapToEntity(userLoginDto);
            user.setUserId(UserIdGenerator.generateNewUserId());
            userRepository.save(user);
            return true;
        }
        else{//return http code if exists or bad email
            return false;
        }

    }

    @Override
    public UserLoginTokenDto loginUser(UserLoginDto userLoginDto) {
        User user= userRepository.findByLogin(userLoginDto.getLogin());
        if(user != null){
            if(user.getLogin().equals(userLoginDto.getLogin()) && user.getPassword().equals(userLoginDto.getPassword())){
                String token = TokenGenerator.generateNewToken();
                user.setToken(token);
                userRepository.save(user);
                return new UserLoginTokenDto(token);
            }
        }
        //checkif email match
        //checkif passhash match
        //generateToken
        return null;
    }


    @Override
    public boolean checkToken(String token) {
        User user = userRepository.findByToken(token);
        if(user != null){
            if(user.getToken().equals(token)) {
                return true;
            }
        }
        return false;
    }



    @Override
    public boolean verifyEmail() {
        return false;
    }
}
