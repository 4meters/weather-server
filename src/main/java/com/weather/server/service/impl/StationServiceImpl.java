package com.weather.server.service.impl;

import com.weather.server.domain.dto.admin.RemoveStationDto;
import com.weather.server.domain.dto.station.StationChangeNameDto;
import com.weather.server.domain.dto.station.StationSetVisibilityDto;
import com.weather.server.domain.dto.station.*;
import com.weather.server.domain.dto.user.UserBookmarkStationListDetailsDto;
import com.weather.server.domain.dto.user.UserMyStationListDetailsDto;
import com.weather.server.domain.dto.user.UserStationListDto;
import com.weather.server.domain.entity.Measure;
import com.weather.server.domain.entity.Station;
import com.weather.server.domain.entity.UserStationList;
import com.weather.server.domain.entity.User;
import com.weather.server.domain.mapper.StationDtoMapper;
import com.weather.server.domain.mapper.StationListDtoMapper;
import com.weather.server.domain.repository.MeasureRepository;
import com.weather.server.domain.repository.UserStationListRepository;
import com.weather.server.domain.repository.StationRepository;
import com.weather.server.domain.repository.UserRepository;
import com.weather.server.service.MeasureService;
import com.weather.server.service.StationService;
import com.weather.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class StationServiceImpl implements StationService {
    private final StationRepository stationRepository;
    private final UserStationListRepository userStationListRepository;
    private final UserRepository userRepository;
    private final MeasureRepository measureRepository;
    private final UserService userService;
    private final MeasureService measureService;
    private final StationListDtoMapper stationListDtoMapper;


    @Autowired
    public StationServiceImpl(StationRepository stationRepository, UserStationListRepository stationListRepository,
                              UserRepository userRepository, MeasureRepository measureRepository, UserService userService, MeasureService measureService, StationListDtoMapper stationListDtoMapper) {
        this.stationRepository = stationRepository;
        this.userStationListRepository = stationListRepository;
        this.userRepository = userRepository;
        this.measureRepository = measureRepository;
        this.userService = userService;
        this.measureService = measureService;
        this.stationListDtoMapper = stationListDtoMapper;
    }

    @Override
    public StationListDto getStationList(String token) {//TODO sth alternative version with all stations public and private in one
        List<Station> stationList;
        stationList = stationRepository.findAll();
        return stationListDtoMapper.mapToDto(stationList);
    }

    @Override
    public StationListDto getPublicStationList() {
        List<Station> stationList;

        stationList = stationRepository.findAllByVisible(true);

        return stationListDtoMapper.mapToDto(stationList);
    }

    @Override
    public StationNameDto getStationName(String stationId) {
        Station station = stationRepository.findByStationId(stationId);
        if(station!=null){
            return new StationNameDto.Builder().stationName(station.getStationName()).build();
        }
        else{
            return null;
        }

    }

    @Override
    public boolean verifyStationId(String stationId) {//TODO refactor
        Station station=stationRepository.findByStationId(stationId);
        if(station == null){
            return false;
        }
        else return station.getStationId().equals(stationId);
    }


    @Override
    public boolean verifyStationIdAndStationKey(VerifyStationDto verifyStationDto) {
        String stationId=verifyStationDto.getStationId();
        String stationKey=verifyStationDto.getStationKey();
        String token=verifyStationDto.getToken();

        Station station=stationRepository.findByStationId(stationId);
        User user = userRepository.findByToken(token);

        if(station == null){
            return false;
        }
        else if(station.getStationId().equals(stationId)&&station.getStationKey().equals(stationKey)) {
            return user != null;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean addStationOnMap(AddStationDto addStationDto) {
        Station station = stationRepository.findByStationId(addStationDto.getStationId());
        User user = userRepository.findByToken(addStationDto.getToken());
        if(user!=null && station!=null){
            List<String> myStationList = new ArrayList<>();
            UserStationList userStationList = userStationListRepository.findByUserId(user.getUserId());
            if(station.getAssigned()!=null && station.getAssigned()==true){
                return false;
            }
            if(userStationList==null){
                userStationList = new UserStationList();
                userStationList.setUserId(user.getUserId());
            }
            else{
                myStationList= userStationList.getMyStationList();
            }

            myStationList.add(addStationDto.getStationId());
            userStationList.setMyStationList(myStationList);

            station.setStationName(addStationDto.getStationName());
            station.setLat(addStationDto.getLat());
            station.setLng(addStationDto.getLng());
            station.setVisible(addStationDto.getVisible());
            station.setAssigned(true);
            stationRepository.save(station);
            userStationListRepository.save(userStationList);
            return true;
        }
        else{
            return false;
        }//TODO return exact message if station is assigned already or sth

    }


    @Override
    public UserStationListDto getUserStationList(String token) {
        List<StationDto> myStationListForDto=new ArrayList<>();
        List<StationDto> bookmarkStationListForDto=new ArrayList<>();
        List<Station> stationList=new ArrayList<>();
        StationDtoMapper stationDtoMapper = new StationDtoMapper();

        if(userService.checkToken(token)){
            User user = userRepository.findByToken(token);
            UserStationList userStationList = userStationListRepository.findByUserId(user.getUserId());
            if(userStationList!=null){
                List<String> myStationList = userStationList.getMyStationList();
                List<String> bookmarkStationList = userStationList.getBookmarkStationList();

                if(myStationList!=null && !myStationList.isEmpty()){
                    stationList = stationRepository.findAll();
                    for(Station station : stationList){
                        for(String stationId : myStationList){
                            if(station.getStationId().equals(stationId)){
                                myStationListForDto.add(stationDtoMapper.mapToDto(station));
                            }
                        }
                    }
                }

                if(bookmarkStationList!=null && !bookmarkStationList.isEmpty()){
                    stationList = stationRepository.findAll();
                    for(Station station : stationList){
                        for(String stationId : bookmarkStationList){
                            if(station.getStationId().equals(stationId)){
                                bookmarkStationListForDto.add(stationDtoMapper.mapToDto(station));
                            }
                        }
                    }
                }

                return new UserStationListDto.Builder()
                        .myStationList(myStationListForDto)
                        .bookmarkStationList(bookmarkStationListForDto)
                        .build();
            }
            else{
                return new UserStationListDto.Builder()
                        .myStationList(new ArrayList<>())
                        .bookmarkStationList(new ArrayList<>())
                        .build();
            }

        }

        return null;
    }

    @Override
    public boolean modeSwitch(StationModeSwitchDto stationModeSwitchDto) {
        if(userService.checkToken(stationModeSwitchDto.getToken())) {
            if (verifyStationId(stationModeSwitchDto.getStationId())) {
                Station station = stationRepository.findByStationId(stationModeSwitchDto.getStationId());
                switch (stationModeSwitchDto.getMode()) {
                    case "disable": {
                        station.setActive(false);
                        stationRepository.save(station);
                        break;
                    }
                    case "enable": {
                        station.setActive(true);
                        stationRepository.save(station);
                        break;
                    }
                }
                return true;
            } else {
                return false;
            }
        }
        else{
            return false;
        }
    }

    @Override
    public boolean setMeasureInterval(StationSetMeasureIntervalDto stationSetMeasureIntervalDto) {
        List<String> availableMeasureIntervals = Arrays.asList("3min","5min","10min","15min");

        //TODO verify if station is in user list
        if(userService.checkToken(stationSetMeasureIntervalDto.getToken())) {
            if (verifyStationId(stationSetMeasureIntervalDto.getStationId())) {

                Station station = stationRepository.findByStationId(stationSetMeasureIntervalDto.getStationId());

                if(availableMeasureIntervals.contains(stationSetMeasureIntervalDto.getMeasureInterval())){
                    station.setMeasureInterval(stationSetMeasureIntervalDto.getMeasureInterval());
                    stationRepository.save(station);
                    return true;
                }

            } else {
                return false;
            }
        }
        else{
            return false;
        }
        return false;
    }

    @Override
    public StationCurrentModeDto getCurrentMode(String stationId) {
        if (verifyStationId(stationId)) {
            Station station = stationRepository.findByStationId(stationId);
            String mode = station.getActive() ? "enabled" : "disabled";
            return new StationCurrentModeDto.Builder()
                    .stationId(stationId)
                    .mode(mode)
                    .measureInterval(station.getMeasureInterval())
                    .build();
            }
        else {
            return null;
        }
    }

    @Override
    public UserMyStationListDetailsDto getUserMyStationListDetails(String token) {
        if(userService.checkToken(token)) {
            List<Station> myStationListForDto = new ArrayList<>();
            HashMap<String, Measure> measureList = new HashMap<>();

            User user = userRepository.findByToken(token);
            UserStationList userStationList = userStationListRepository.findByUserId(user.getUserId());

            if (userStationList != null) {
                List<String> myStationList = userStationList.getMyStationList();


                if (myStationList != null && !myStationList.isEmpty()) {
                    myStationListForDto = stationRepository.findByStationIdIn(myStationList);
                }

                assert myStationList != null;
                for (String stationId : myStationList) {
                    Station station = stationRepository.findByStationId(stationId);
                    float elevation = measureService.getElevation(station.getLat(),station.getLng());//pressure elev fix
                    Measure measure = measureRepository.findLastMeasureByStationId(stationId, elevation);
                    if (measure != null) {
                        measureList.put(stationId, measure);
                    }
                }
                return new UserMyStationListDetailsDto.Builder()
                        .stationList(myStationListForDto)
                        .measureList(measureList)
                        .build();
                //get measures

            }
        }
        return null;
    }

    @Override
    public boolean changeStationName(StationChangeNameDto stationChangeNameDto) {
        if(userService.checkToken(stationChangeNameDto.getToken())){
            Station station = stationRepository.findByStationId(stationChangeNameDto.getStationId());
            if(station!=null){
                User user = userRepository.findByToken(stationChangeNameDto.getToken());
                UserStationList userStationList = userStationListRepository.findByUserId(user.getUserId());
                if(userStationList!=null){
                    List<String> myStationList = userStationList.getMyStationList();
                    if(myStationList!=null){
                        if(myStationList.contains(stationChangeNameDto.getStationId())){
                            station.setStationName(stationChangeNameDto.getNewStationName());
                            stationRepository.save(station);
                            return true;
                        }
                    }
                }
            }
        }
        return false;

    }

    @Override
    public boolean setVisibility(StationSetVisibilityDto stationSetVisibilityDto) {
        if(userService.checkToken(stationSetVisibilityDto.getToken())){
            Station station = stationRepository.findByStationId(stationSetVisibilityDto.getStationId());
            if(station!=null){
                User user = userRepository.findByToken(stationSetVisibilityDto.getToken());
                UserStationList userStationList = userStationListRepository.findByUserId(user.getUserId());
                if(userStationList!=null){
                    List<String> myStationList = userStationList.getMyStationList();
                    if(myStationList!=null){
                        if(myStationList.contains(stationSetVisibilityDto.getStationId())){
                            station.setVisible(stationSetVisibilityDto.getVisibility().equals("true")); //string to bool
                            stationRepository.save(station);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ResponseEntity<?> removeStation(RemoveStationDto removeStationDto) {
        if(userService.checkToken(removeStationDto.getToken())){
            Station station = stationRepository.findByStationId(removeStationDto.getStationId());
            if(station!=null){
                station.setAssigned(false);
                station.setVisible(null);
                station.setLat(null);
                station.setLng(null);
                station.setMeasureInterval(null);
                station.setStationName(null);

                stationRepository.save(station);

                if(removeStationDto.getRemoveMeasures()){
                    measureRepository.deleteByStationId(removeStationDto.getStationId());
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
    public UserBookmarkStationListDetailsDto getUserBookmarkStationListDetails(String token) {
        if(userService.checkToken(token)) {
            List<Station> bookmarkStationListForDto = new ArrayList<>();
            HashMap<String, Measure> measureList = new HashMap<>();

            User user = userRepository.findByToken(token);
            UserStationList userStationList = userStationListRepository.findByUserId(user.getUserId());

            if (userStationList != null) {
                List<String> bookmarkStationList = userStationList.getBookmarkStationList();


                if (bookmarkStationList != null && !bookmarkStationList.isEmpty()) {
                    bookmarkStationListForDto = stationRepository.findByStationIdIn(bookmarkStationList);
                }

                assert bookmarkStationList != null;
                for (String stationId : bookmarkStationList) {
                    Station station = stationRepository.findByStationId(stationId);
                    float elevation = measureService.getElevation(station.getLat(),station.getLng());//pressure elev fix
                    Measure measure = measureRepository.findLastMeasureByStationId(stationId, elevation);
                    if (measure != null) {
                        measureList.put(stationId, measure);
                    }
                }
                return new UserBookmarkStationListDetailsDto.Builder()
                        .stationList(bookmarkStationListForDto)
                        .measureList(measureList)
                        .build();

            }
        }
        return null;
    }

}
