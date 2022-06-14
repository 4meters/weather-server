package com.weather.server.controller;

import com.weather.server.domain.dto.admin.AddStationToDbDto;
import com.weather.server.domain.dto.admin.RemoveStationDto;
import com.weather.server.domain.dto.admin.RemoveUserDto;
import com.weather.server.domain.dto.admin.ResetUserPasswordDto;
import com.weather.server.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/admin")
public class AdminApiController {
    private AdminService adminService;

    public AdminApiController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(value = "/add-station-to-db")
    public ResponseEntity<?> addStationToDb(@RequestBody AddStationToDbDto addStationToDbDto){
        return adminService.addStationToDb(addStationToDbDto);
    }

    @PostMapping(value = "/remove-station")
    public ResponseEntity<?> removeStation(@RequestBody RemoveStationDto removeStationDto){
        return adminService.removeStation(removeStationDto);
    }

    @PostMapping(value = "/remove-station-from-db")
    public ResponseEntity<?> removeStationFromDb(@RequestBody RemoveStationDto removeStationDto){
        return adminService.removeStationFromDb(removeStationDto);
    }

    @PostMapping(value = "/remove-user")
    public ResponseEntity<?> removeUser(@RequestBody RemoveUserDto removeUserDto){
        return adminService.removeUser(removeUserDto);
    }

    @PostMapping(value = "/reset-user-password")
    public ResponseEntity<?> resetUserPassword(@RequestBody ResetUserPasswordDto resetUserPasswordDto){
        return adminService.resetUserPassword(resetUserPasswordDto);
    }

    @GetMapping(value = "/get-all-users")
    public ResponseEntity<?> getAllUsers(@RequestParam String token){
        return adminService.getAllUsers(token);
    }

    @GetMapping(value = "/get-all-stations")
    public ResponseEntity<?> getAllStations(@RequestParam String token){
        return adminService.getAllStations(token);
    }

    @GetMapping(value = "/verify-admin")
    public ResponseEntity<?> verifyAdmin(@RequestParam String token){
        return adminService.checkIfAdmin(token) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
