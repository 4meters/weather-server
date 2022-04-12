package com.weather.server.service.impl;

import com.weather.server.domain.dto.station.AddStationDto;
import com.weather.server.domain.dto.station.StationListDto;
import com.weather.server.domain.dto.station.StationNameDto;
import com.weather.server.domain.dto.station.VerifyStationDto;
import com.weather.server.domain.dto.user.UserStationListDto;
import com.weather.server.domain.entity.Station;
import com.weather.server.domain.entity.UserStationList;
import com.weather.server.domain.entity.User;
import com.weather.server.domain.repository.UserStationListRepository;
import com.weather.server.domain.repository.StationRepository;
import com.weather.server.domain.repository.UserRepository;
import com.weather.server.service.StationService;
import com.weather.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StationServiceImpl implements StationService {
    private final StationRepository stationRepository;
    private final UserStationListRepository userStationListRepository;
    private final UserRepository userRepository;
    private final UserService userService;


    @Autowired
    public StationServiceImpl(StationRepository stationRepository, UserStationListRepository stationListRepository, UserRepository userRepository, UserService userService) {
        this.stationRepository = stationRepository;
        this.userStationListRepository = stationListRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public List<Station> getStationList(String token) {//TODO sth
        List<Station> stationList;
        //if(token==null || token.equals("")){
         //   stationList =  stationRepository.findAllByVisibility(true); //visible
        //}
        //else{
            stationList = stationRepository.findAll();
        //}
        return stationList;
    }

    @Override
    public List<Station> getPublicStationList() {//TODO sth
        List<Station> stationList;
        //if(token==null || token.equals("")){
        //   stationList =  stationRepository.findAllByVisibility(true); //visible
        //}
        //else{
        stationList = stationRepository.findAllByVisible(true);
        //}
        return stationList;
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
    public boolean verifyStationId(VerifyStationDto verifyStationDto) {
        String stationId=verifyStationDto.getStationId();
        String stationKey=verifyStationDto.getStationKey();
        String token=verifyStationDto.getToken();

        Station station=stationRepository.findByStationId(stationId);
        User user = userRepository.findByToken(token);

        //System.out.println(stationRepository.findAllByVisible(true));
        //System.out.println("TEST2");
        //System.out.println(station);
        //System.out.println(stationId);
        //TODO refactor
        if(station == null){
            //System.out.println("if1st");
            return false;
        }
        else if(station.getStationId().equals(stationId)&&station.getStationKey().equals(stationKey)) {
            if(user==null){
                return false;
            }
            else {
                //System.out.println("else ifst");
                return true;
            }

        }
        else{
            //System.out.println("elsest");
            return false;
        }
    }

    @Override
    public boolean addStationOnMap(AddStationDto addStationDto) { //TODO on remove/remove from user list and map set active to false, assigned to false
        Station station = stationRepository.findByStationId(addStationDto.getStationId());
        User user = userRepository.findByToken(addStationDto.getToken());
        //TODO add check if station is already added on map and assigned to user (lat lng not null and stationId present in user stationIdlist)
        if(user!=null && station!=null){
            List<String> myStationList = new ArrayList<>();
            UserStationList userStationList = userStationListRepository.findByUserId(user.getUserId());
            if(userStationList==null){
                userStationList = new UserStationList();
                userStationList.setUserId(user.getUserId());
            }
            else{
                myStationList= userStationList.getMyStationList();
            }
            //TODO ->>
            //userStationIdList.contains --> stationRepository.findByStationId -->then--> check assigned status and lat lng if false and null null
            myStationList.add(addStationDto.getStationId());
            userStationList.setMyStationList(myStationList);

            station.setStationName(addStationDto.getStationName());
            station.setLat(addStationDto.getLat());
            station.setLng(addStationDto.getLng());
            station.setVisible(addStationDto.getVisible());
            stationRepository.save(station);
            userStationListRepository.save(userStationList);
            //userRepository.save(user);
            return true;
        }
        else{
            return false;
        }//TODO return exact message if station is assigned already or sth

    }


    @Override
    public UserStationListDto getUserStationList(String token) {
        List<Station> myStationListForDto=new ArrayList<>();
        List<Station> bookmarkStationListForDto=new ArrayList<>();

        if(userService.checkToken(token)){
            User user = userRepository.findByToken(token);
            UserStationList userStationList = userStationListRepository.findByUserId(user.getUserId());
            System.out.println("CHECK1");
            if(userStationList!=null){
                List<String> myStationList = userStationList.getMyStationList();
                List<String> bookmarkStationList = userStationList.getBookmarkStationList();
                System.out.println("CHECK2");

                if(myStationList!=null && !myStationList.isEmpty()){
                    myStationListForDto = stationRepository.findAll();
                    for(String stationId : myStationList){
                        myStationListForDto.removeIf(station -> !station.getStationId().equals(stationId));
                    }
                }            System.out.println("CHECK3");

                if(bookmarkStationList!=null && !bookmarkStationList.isEmpty()){
                    bookmarkStationListForDto = stationRepository.findAll();
                    for(String stationId : bookmarkStationList){
                        bookmarkStationListForDto.removeIf(station -> !station.getStationId().equals(stationId));
                    }
                }            System.out.println("CHECK4");

                return new UserStationListDto.Builder()
                        .myStationList(myStationListForDto)
                        .bookmarkStationList(bookmarkStationListForDto)
                        .build();
            }

        }
        System.out.println("CHECK5");

        return null;
    }
    /*public boolean verifyStationId(String stationId) {
        Station station = stationRepository.findByStationId(stationId);
        if(station!=null){
            if(station.getStationId().equals(stationId)){
                return true;
            }
            else return false;
        }
        return false;


    }*/


}
