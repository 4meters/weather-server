package com.weather.server.service.impl;

import com.weather.server.domain.dto.UserApiKeyDto;
import com.weather.server.domain.dto.UserLoginDto;
import com.weather.server.domain.dto.UserLoginTokenDto;
import com.weather.server.domain.entity.User;
import com.weather.server.domain.mapper.UserMapper;
import com.weather.server.domain.model.TokenGenerator;
import com.weather.server.domain.repository.UserRepository;
import com.weather.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository=userRepository;
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
        if(userRepository.findByEmail(userLoginDto.getEmail())==null){
            User user = new UserMapper().mapToEntity(userLoginDto);
            userRepository.save(user);
            return true;
        }
        else{
            return false;
        }

    }

    @Override
    public UserLoginTokenDto loginUser(UserLoginDto userLoginDto) {
        User user= userRepository.findByEmail(userLoginDto.getEmail());
        if(user != null){
            if(user.getEmail().equals(userLoginDto.getEmail()) && user.getPassword().equals(userLoginDto.getPassword())){
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
    public boolean generateApiKey(UserLoginTokenDto userLoginTokenDto){
        User user= userRepository.findByToken(userLoginTokenDto.getToken());
        if(user != null){
            if(user.getToken().equals(userLoginTokenDto.getToken())){
                String apiKey = TokenGenerator.generateNewToken();
                apiKey = apiKey.toUpperCase();
                user.setApiKey(apiKey);
                userRepository.save(user);
                return true;
            }
        }
        //find user by token
        //generate api key and save in mongodb
        return false;
    }

    @Override
    public UserApiKeyDto readApiKey(String token) {
        User user = userRepository.findByToken(token);
        if(user != null){
            if(user.getToken().equals(token)){
                if(user.getApiKey() != null){
                    return user.getApiKey().isEmpty() ? null : new UserApiKeyDto(user.getApiKey());
                }
            }
        }
        //find user by token
        //read api key and send it back
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
