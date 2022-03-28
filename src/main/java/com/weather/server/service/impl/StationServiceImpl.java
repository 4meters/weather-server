package com.weather.server.service.impl;

import com.weather.server.domain.dto.station.VerifyStationDto;
import com.weather.server.domain.entity.Station;
import com.weather.server.domain.entity.User;
import com.weather.server.domain.repository.StationRepository;
import com.weather.server.domain.repository.UserRepository;
import com.weather.server.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {
    private final StationRepository stationRepository;
    private final UserRepository userRepository;


    @Autowired
    public StationServiceImpl(StationRepository stationRepository, UserRepository userRepository) {
        this.stationRepository = stationRepository;
        this.userRepository = userRepository;
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
    public boolean verifyStationId(VerifyStationDto verifyStationDto) {
        String stationId=verifyStationDto.getStationId();
        String token=verifyStationDto.getToken();

        Station station=stationRepository.findByStationId(stationId);
        User user = userRepository.findByToken(token);

        System.out.println(stationRepository.findAllByVisible(true));
        System.out.println("TEST2");
        System.out.println(station);
        System.out.println(stationId);
        if(station == null){
            System.out.println("if1st");
            return false;
        }
        else if(station.getStationId().equals(stationId)) {
            if(user==null){
                return false;
            }
            else {
                System.out.println("else ifst");
                return true;
            }

        }
        else{
            System.out.println("elsest");
            return false;
        }
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
