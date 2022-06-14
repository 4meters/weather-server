package com.weather.server.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.server.domain.dto.chart.ChartListDto;
import com.weather.server.domain.dto.measure.*;
import com.weather.server.domain.entity.*;
import com.weather.server.domain.mapper.ChartDtoMapper;
import com.weather.server.domain.mapper.LastMeasureDtoMapper;
import com.weather.server.domain.mapper.NewMeasureMapper;
import com.weather.server.domain.repository.MeasureRepository;
import com.weather.server.domain.repository.StationRepository;
import com.weather.server.domain.repository.UserRepository;
import com.weather.server.domain.repository.UserStationListRepository;
import com.weather.server.service.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.time.Instant;
import java.util.*;

@Service
public class MeasureServiceImpl implements MeasureService {

    private final MeasureRepository measureRepository;
    private final UserRepository userRepository;
    private final UserStationListRepository userStationListRepository;
    private final StationRepository stationRepository;
    private final ChartDtoMapper chartDtoMapper;
    private final LastMeasureDtoMapper lastMeasureDtoMapper;
    private final NewMeasureMapper newMeasureMapper;



    @Autowired
    public MeasureServiceImpl(MeasureRepository measureRepository, UserRepository userRepository, UserStationListRepository userStationListRepository, StationRepository stationRepository, ChartDtoMapper chartDtoMapper, LastMeasureDtoMapper lastMeasureDtoMapper, NewMeasureMapper newMeasureMapper) {
        this.measureRepository=measureRepository;
        this.userRepository = userRepository;
        this.userStationListRepository = userStationListRepository;
        this.stationRepository = stationRepository;
        this.chartDtoMapper = chartDtoMapper;
        this.lastMeasureDtoMapper = lastMeasureDtoMapper;
        this.newMeasureMapper = newMeasureMapper;
    }


    @Override
    public boolean saveMeasure(NewMeasureDto newMeasureDto) {
        if(verifyStationId(newMeasureDto.getStationId())) {
            Measure measure = newMeasureMapper.mapToEntity(newMeasureDto);
            measureRepository.save(measure);
            return true;
        }
        else{
            return false;
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
    public LastMeasureDto getLastMeasure(String stationId) {
        Station station = stationRepository.findByStationId(stationId);
        float elevation = getElevation(station.getLat(), station.getLng());
        Measure measure =measureRepository.findLastMeasureByStationId(stationId, elevation);
        return lastMeasureDtoMapper.mapToDto(measure);
    }

    @Override
    public MeasureListDto getMeasureListByDate(MeasureByDateDto measureByDateDto) {
        Station station = stationRepository.findByStationId(measureByDateDto.getStationId());
        if(!station.getVisible()){ //if private station verify token
            User user = userRepository.findByToken(measureByDateDto.getToken());
            if(user==null){
                return null;
            }
        }

        if(verifyStationId(measureByDateDto.getStationId())) {
            Date dateFrom = Date.from(Instant.parse(measureByDateDto.getDateFrom()));
            Date dateTo = Date.from(Instant.parse(measureByDateDto.getDateTo()));

            String stationId = measureByDateDto.getStationId();
            List<Measure> measureList = measureRepository.findByDateBetween(dateFrom, dateTo, stationId);

            return new MeasureListDto.Builder()
                    .measureList(measureList)
                    .build();

        }
        else{
            return null;
        }


    }

    @Override
    public float getElevation(String lat, String lng){
        //get elevation from remote
        float elevation = (float)200.0; //default set in case remote server fails
        final String uri = "https://api.airmap.com/elevation/v1/ele/?points="+lat+","+lng;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        ObjectMapper mapper = new ObjectMapper();
        try{
            JsonNode root = mapper.readTree(Objects.requireNonNull(response.getBody()));
            JsonNode results = root.path("data");

            if (results.isArray()) {
                Iterator<JsonNode> itr = results.iterator();
                JsonNode item=itr.next();
                String elevationStr = item.toString();
                elevation=Float.parseFloat(elevationStr);
                return elevation;
            }
        }
        catch (Exception e){
            System.out.println("Error parsing JSON");
        }

        return elevation;
    }

    @Override
    public ChartListDto getMeasuresForChart(MeasureByDateChartDto measureByDateChartDto) {

        Station station = stationRepository.findByStationId(measureByDateChartDto.getStationId());
        if(!station.getVisible()){ //if private station verify token
            if(measureByDateChartDto.getToken()!=null) {
                User user = userRepository.findByToken(measureByDateChartDto.getToken());
                if (user == null) {
                    return null;//403 http code?
                }
            }
        }
        if(verifyStationId(measureByDateChartDto.getStationId())) {
            Date dateFrom = Date.from(Instant.parse(measureByDateChartDto.getDateFrom()));
            Date dateTo = Date.from(Instant.parse(measureByDateChartDto.getDateTo()));
            String stationId = measureByDateChartDto.getStationId();
            String timezone = measureByDateChartDto.getTimezone();

            //Measure measure=null;
            List<MeasureAggregation> measureList=null;

            if(dateFrom.getTime() < dateTo.getTime()){
                long dateDiff = dateTo.getTime() - dateFrom.getTime();
                long days_difference = (dateDiff / (1000*60*60*24));
                long hours_difference = (dateDiff / (1000*60*60));
                if(hours_difference<1){
                    return null;
                }
                else if(days_difference>=0 && days_difference<=7){

                    switch(measureByDateChartDto.getChartType()){
                        case "avg":{
                            switch(measureByDateChartDto.getChartValue()){
                                case "temp":{
                                    measureList=measureRepository.findAvgTempByDateBetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "humidity":{
                                    measureList=measureRepository.findAvgHumidityByDateBetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pressure":{
                                    Float elevation = getElevation(station.getLat(), station.getLng());
                                    measureList=measureRepository.findAvgPressureByDateBetweenGroupByHour(dateFrom, dateTo, stationId, timezone, elevation);
                                    break;
                                }
                                case "pm10":{
                                    measureList=measureRepository.findAvgPM10ByDateBetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25":{
                                    measureList=measureRepository.findAvgPM25ByDateBetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25Corr":{
                                    measureList=measureRepository.findAvgPM25CorrByDateBetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }


                            }
                            break;
                        }
                        case "min":{
                            switch(measureByDateChartDto.getChartValue()){
                                case "temp":{
                                    measureList=measureRepository.findMinTempByDateBetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "humidity":{
                                    measureList=measureRepository.findMinHumidityByDateBetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pressure":{
                                    Float elevation = getElevation(station.getLat(), station.getLng());
                                    measureList=measureRepository.findMinPressureByDateBetweenGroupByHour(dateFrom, dateTo, stationId, timezone, elevation);
                                    break;
                                }
                                case "pm10":{
                                    measureList=measureRepository.findMinPM10ByDateBetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25":{
                                    measureList=measureRepository.findMinPM25ByDateBetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25Corr":{
                                    measureList=measureRepository.findMinPM25CorrByDateBetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                            }
                            break;
                        }
                        case "max": {
                            switch(measureByDateChartDto.getChartValue()){
                                case "temp":{
                                    measureList=measureRepository.findMaxTempByDateBetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "humidity":{
                                    measureList=measureRepository.findMaxHumidityByDateBetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pressure":{
                                    Float elevation = getElevation(station.getLat(), station.getLng());
                                    measureList=measureRepository.findMaxPressureByDateBetweenGroupByHour(dateFrom, dateTo, stationId, timezone, elevation);
                                    break;
                                }
                                case "pm10":{
                                    measureList=measureRepository.findMaxPM10ByDateBetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25":{
                                    measureList=measureRepository.findMaxPM25ByDateBetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25Corr":{
                                    measureList=measureRepository.findMaxPM25CorrByDateBetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    //mapper here
                    return chartDtoMapper.mapToDto(measureByDateChartDto.getChartValue(),measureList);


                    //return new ChartListDto(chartTempList);


                }
                else{//more than week

                    switch(measureByDateChartDto.getChartType()){
                        case "avg":{
                            switch(measureByDateChartDto.getChartValue()){
                                case "temp":{
                                    measureList=measureRepository.findAvgTempByDateBetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "humidity":{
                                    measureList=measureRepository.findAvgHumidityByDateBetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pressure":{
                                    Float elevation = getElevation(station.getLat(), station.getLng());
                                    measureList=measureRepository.findAvgPressureByDateBetweenGroupByDay(dateFrom, dateTo, stationId, timezone, elevation);
                                    break;
                                }
                                case "pm10":{
                                    measureList=measureRepository.findAvgPM10ByDateBetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25":{
                                    measureList=measureRepository.findAvgPM25ByDateBetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25Corr":{
                                    measureList=measureRepository.findAvgPM25CorrByDateBetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }


                            }
                            break;
                        }
                        case "min":{
                            switch(measureByDateChartDto.getChartValue()){
                                case "temp":{
                                    measureList=measureRepository.findMinTempByDateBetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "humidity":{
                                    measureList=measureRepository.findMinHumidityByDateBetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pressure":{
                                    Float elevation = getElevation(station.getLat(), station.getLng());
                                    measureList=measureRepository.findMinPressureByDateBetweenGroupByDay(dateFrom, dateTo, stationId, timezone, elevation);
                                    break;
                                }
                                case "pm10":{
                                    measureList=measureRepository.findMinPM10ByDateBetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25":{
                                    measureList=measureRepository.findMinPM25ByDateBetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25Corr":{
                                    measureList=measureRepository.findMinPM25CorrByDateBetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                            }
                            break;
                        }
                        case "max": {
                            switch(measureByDateChartDto.getChartValue()){
                                case "temp":{
                                    measureList=measureRepository.findMaxTempByDateBetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "humidity":{
                                    measureList=measureRepository.findMaxHumidityByDateBetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pressure":{
                                    Float elevation = getElevation(station.getLat(), station.getLng());
                                    measureList=measureRepository.findMaxPressureByDateBetweenGroupByDay(dateFrom, dateTo, stationId, timezone, elevation);
                                    break;
                                }
                                case "pm10":{
                                    measureList=measureRepository.findMaxPM10ByDateBetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25":{
                                    measureList=measureRepository.findMaxPM25ByDateBetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25Corr":{
                                    measureList=measureRepository.findMaxPM25CorrByDateBetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    return chartDtoMapper.mapToDto(measureByDateChartDto.getChartValue(),measureList);
                }
            }


        }
        else{
            return null;
        }

        return null;
    }


    @Override
    public boolean saveMeasurePackage(List<NewMeasureDto> newMeasureDtoList) {

        ArrayList<String> stationIdList = new ArrayList<>();

        //verify stationId for package measures with caching stationIds in list - reduce db queries
        for(NewMeasureDto newMeasureDto : newMeasureDtoList){
            if(!stationIdList.contains(newMeasureDto.getStationId())){
                Station station = stationRepository.findByStationId(newMeasureDto.getStationId());
                if(station!=null){
                    stationIdList.add(station.getStationId());
                }
                else {
                    return false;
                }
            }
        }

        List<Measure> measureList=new ArrayList<>();
        for(NewMeasureDto newMeasureDto : newMeasureDtoList){
            Measure measure = newMeasureMapper.mapToEntity(newMeasureDto);
            measureList.add(measure);
        }
        measureRepository.insert(measureList);
        return true;
    }

    @Override
    public MeasureListDto getMeasureDatabase() {
        List<Measure> measureList = measureRepository.findAll();

        return new MeasureListDto.Builder()
                .measureList(measureList)
                .build();
    }

    @Override
    public LastMeasureListDto getLastMeasureAllPublic() {
        List<Station> stationList = stationRepository.findAllByVisible(true);
        HashMap<String, Measure> measureList = createMeasureList(stationList);

        return new LastMeasureListDto.Builder().measureList(measureList).build();
    }

    public HashMap<String, Measure> createMeasureList(List<Station> stationList){
        HashMap<String, Measure> measureList = new HashMap<>();
        Measure measure;
        for (Station station : stationList){

            float elevation = getElevation(station.getLat(), station.getLng());
            measure = measureRepository.findLastMeasureByStationId(station.getStationId(), elevation);
            if(measure!=null){
                measureList.put(station.getStationId(), measure);
            }

        }
        return measureList;
    }

}
