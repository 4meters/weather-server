package com.weather.server.service;

import com.weather.server.domain.dto.admin.AddStationToDbDto;
import com.weather.server.domain.dto.admin.RemoveStationDto;
import com.weather.server.domain.dto.admin.RemoveUserDto;
import com.weather.server.domain.dto.admin.ResetUserPasswordDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface AdminService {

    ResponseEntity<?> addStationToDb(AddStationToDbDto addStationToDbDto);
    ResponseEntity<?> removeStation(RemoveStationDto removeStationDto);
    ResponseEntity<?> removeStationFromDb(RemoveStationDto removeStationDto);
    ResponseEntity<?> removeUser(RemoveUserDto removeUserDto);
    ResponseEntity<?> resetUserPassword(ResetUserPasswordDto resetUserPasswordDto);
    ResponseEntity<?> getAllUsers(String token);
    ResponseEntity<?> getAllStations(String token);
    boolean checkIfAdmin(String token);
}
