package com.weather.server.service.impl;

import com.weather.server.domain.entity.Station;
import com.weather.server.domain.repository.StationRepository;
import com.weather.server.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {
    private final StationRepository stationRepository;

    @Autowired
    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
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

}
