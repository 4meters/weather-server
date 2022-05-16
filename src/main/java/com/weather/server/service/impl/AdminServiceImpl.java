package com.weather.server.service.impl;

import com.weather.server.domain.dto.admin.*;
import com.weather.server.domain.entity.Station;
import com.weather.server.domain.entity.User;
import com.weather.server.domain.mapper.StationListDtoMapper;
import com.weather.server.domain.mapper.UserMapper;
import com.weather.server.domain.model.PasswordGenerator;
import com.weather.server.domain.repository.MeasureRepository;
import com.weather.server.domain.repository.StationRepository;
import com.weather.server.domain.repository.UserRepository;
import com.weather.server.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final StationRepository stationRepository;
    private final MeasureRepository measureRepository;
    private final UserMapper userMapper;
    private final StationListDtoMapper stationListDtoMapper;

    public AdminServiceImpl(UserRepository userRepository, StationRepository stationRepository, MeasureRepository measureRepository, UserMapper userMapper, StationListDtoMapper stationListDtoMapper) {
        this.userRepository = userRepository;
        this.stationRepository = stationRepository;
        this.measureRepository = measureRepository;
        this.userMapper = userMapper;
        this.stationListDtoMapper = stationListDtoMapper;
    }


    @Override
    public ResponseEntity<?> addStationToDb(AddStationToDbDto addStationToDbDto) {
        if(checkIfAdmin(addStationToDbDto.getToken())){
            Station station = stationRepository.findByStationId(addStationToDbDto.getStationId());
            if(station==null){
                Station newStation = new Station();
                newStation.setStationId(addStationToDbDto.getStationId());
                newStation.setStationKey(addStationToDbDto.getStationKey());
                stationRepository.save(newStation);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @Override
    public ResponseEntity<?> removeStation(RemoveStationDto removeStationDto) {
        if(checkIfAdmin(removeStationDto.getToken())){
            Station station = stationRepository.findByStationId(removeStationDto.getStationId());
            if(station!=null){
                stationRepository.delete(station);
                if(removeStationDto.getRemoveMeasures()){
                    //<<<------->>>//measureRepository.deleteByStationId(removeStationDto.getStationId()); //TODO uncomment and test
                    System.out.println("TODO");
                }
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<?> removeUser(RemoveUserDto removeUserDto) {
        if(checkIfAdmin(removeUserDto.getToken())){
            User user = userRepository.findByUserId(removeUserDto.getUserId());
            if(user!=null){
                userRepository.delete(user);

                //TODO my stations -> reset isAssigned to false
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<?> resetUserPassword(ResetUserPasswordDto resetUserPasswordDto) {
        if(checkIfAdmin(resetUserPasswordDto.getToken())){
            User user = userRepository.findByUserId(resetUserPasswordDto.getUserId());
            if(user!=null){
                String newPassword = PasswordGenerator.generatePassword();
                user.setPassword(newPassword);
                userRepository.save(user);

                //TODO my stations -> reset isAssigned to false
                UserNewPassword userNewPassword = new UserNewPassword.Builder().newPassword(newPassword).build();
                return new ResponseEntity<>(userNewPassword, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<?> getAllUsers(String token) {
        if(checkIfAdmin(token)){
            List<User> userList = userRepository.findAll();
            return new ResponseEntity<>(userMapper.mapToDto(userList), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<?> getAllStations(String token) {
        if(checkIfAdmin(token)){
            List<Station> stationList = stationRepository.findAll();
            return new ResponseEntity<>(stationListDtoMapper.mapToDto(stationList), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    private boolean checkIfAdmin(String token){
        if(token==null){
            return false;
        }
        User user = userRepository.findByToken(token);
        if(user!=null){
            if(user.getLogin().equals("admin")){
                return true;
            }
        }
        return false;
    }
}
