package com.weather.server.service.impl;

import com.weather.server.domain.dto.admin.RemoveStationDto;
import com.weather.server.domain.dto.user.*;
import com.weather.server.domain.entity.Station;
import com.weather.server.domain.entity.User;
import com.weather.server.domain.entity.UserStationList;
import com.weather.server.domain.mapper.UserMapper;
import com.weather.server.domain.model.PasswordHasher;
import com.weather.server.domain.model.TokenGenerator;
import com.weather.server.domain.model.UserIdGenerator;
import com.weather.server.domain.repository.StationRepository;
import com.weather.server.domain.repository.UserRepository;
import com.weather.server.domain.repository.UserStationListRepository;
import com.weather.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final StationRepository stationRepository;
    private final UserStationListRepository userStationListRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, StationRepository stationRepository, UserStationListRepository userStationListRepository, UserMapper userMapper) {
        this.userRepository=userRepository;
        this.stationRepository=stationRepository;
        this.userStationListRepository = userStationListRepository;
        this.userMapper = userMapper;
    }

    @Override
    public boolean createUser(UserLoginDto userLoginDto) {

        if(userRepository.findByLogin(userLoginDto.getLogin())==null){
            try{
                if(userLoginDto.getLogin().length()<3 || userLoginDto.getPassword().length()<8){
                    throw new Exception();//maybe add message
                }
                User user = userMapper.mapToEntity(userLoginDto);
                user.setUserId(UserIdGenerator.generateNewUserId());
                userRepository.save(user);
                return true;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                return false;
            }
        }
        else{//return http code if exists or bad email
            return false;
        }

    }

    @Override
    public UserLoginTokenDto loginUser(UserLoginDto userLoginDto) {
        User user= userRepository.findByLogin(userLoginDto.getLogin());
        if(user != null){
            try{
                String hashedPassword =
                        PasswordHasher.hashPasswordWithSpecifiedSalt(
                        userLoginDto.getPassword(),
                        PasswordHasher.getSaltFromDBpassword(user.getPassword()));

                //System.out.println(hashedPassword);
                //System.out.println(user.getPassword());
                if(user.getLogin().equals(userLoginDto.getLogin()) && user.getPassword().equals(hashedPassword)){
                    String token = TokenGenerator.generateNewToken();
                    user.setToken(token);
                    userRepository.save(user);
                    return new UserLoginTokenDto.Builder().token(token).build();
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                return null;
            }

        }

        return null;
    }


    @Override
    public boolean checkToken(String token) {
        User user = userRepository.findByToken(token);
        if(user != null){
            return user.getToken().equals(token);
        }
        return false;
    }


    @Override
    public boolean changePassword(UserPasswordChangeDto userPasswordChangeDto) {
        User user= userRepository.findByToken(userPasswordChangeDto.getToken());
        if(user != null){
            try{
                String hashedPassword =
                        PasswordHasher.hashPasswordWithSpecifiedSalt(
                                userPasswordChangeDto.getOldPassword(),
                                PasswordHasher.getSaltFromDBpassword(user.getPassword()));

                if(user.getToken().equals(userPasswordChangeDto.getToken()) && user.getPassword().equals(hashedPassword)){
                    String newHashedPassword = PasswordHasher.hashNewPassword(userPasswordChangeDto.getNewPassword());
                    user.setPassword(newHashedPassword);
                    userRepository.save(user);
                    return true;
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                return false;
            }

        }
        return false;
    }

    @Override
    public boolean addBookmark(UserBookmarkStation userBookmarkStation) {

        User user = userRepository.findByToken(userBookmarkStation.getToken());
        if(user != null){
            UserStationList userStationList = userStationListRepository.findByUserId(user.getUserId());
            List<String> bookmarkStationList = new ArrayList<>();
            if(userStationList == null){
                userStationList = new UserStationList();
                userStationList.setUserId(user.getUserId());
            }

            if(userStationList.getBookmarkStationList()==null){
                bookmarkStationList.add(userBookmarkStation.getStationId());
                userStationList.setBookmarkStationList(bookmarkStationList);
                userStationListRepository.save(userStationList);
                return true;
            }
            else{
                bookmarkStationList = userStationList.getBookmarkStationList();
                if(bookmarkStationList.contains(userBookmarkStation.getStationId())){
                    return false;
                }
                else{
                    bookmarkStationList.add(userBookmarkStation.getStationId());
                    userStationList.setBookmarkStationList(bookmarkStationList);
                    userStationListRepository.save(userStationList);
                    return true;
                }
                //check for station id
            }
        }
        else{
            return false;
        }

    }

    @Override
    public boolean removeBookmark(UserBookmarkStation userBookmarkStation) {

        User user = userRepository.findByToken(userBookmarkStation.getToken());
        if(user != null){
            UserStationList userStationList = userStationListRepository.findByUserId(user.getUserId());
            List<String> bookmarkStationList = new ArrayList<>();
            if(userStationList == null){
                return false;
            }

            if(userStationList.getBookmarkStationList()==null){
                return false;
            }
            else{
                bookmarkStationList = userStationList.getBookmarkStationList();
                if(bookmarkStationList.contains(userBookmarkStation.getStationId())){
                    bookmarkStationList.remove(userBookmarkStation.getStationId());
                    userStationList.setBookmarkStationList(bookmarkStationList);
                    userStationListRepository.save(userStationList);
                    return true;
                }
                else{
                    return false;
                }
                //check for station id
            }
        }
        else{
            return false;
        }
    }

    @Override
    public ResponseEntity<?> removeStation(RemoveStationDto removeStationDto) {
        return null;
    }
}
