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
    /*public void test(){
        ConnectionString connectionString = new ConnectionString("mongodb+srv://admin:zaq1%40WSX@cluster0.fyl2u.mongodb.net/Cluster0?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("Cluster0");
    }*/

    @Override
    public boolean saveMeasure(NewMeasureDto newMeasureDto) {
        if(verifyStationId(newMeasureDto.getStationId())) {
            //System.out.println("Saving measure");
            Measure measure = newMeasureMapper.mapToEntity(newMeasureDto);
            //System.out.println(measure);
            measureRepository.save(measure);
            return true;
        }
        else{
            //System.out.println("Not saving measure - check stationId");
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
        //System.out.println(stationId);
        Station station = stationRepository.findByStationId(stationId);
        float elevation = getElevation(station.getLat(), station.getLng());
        Measure measure =measureRepository.findLastMeasureByStationId(stationId, elevation);
        //System.out.println(measure);
        return lastMeasureDtoMapper.mapToDto(measure);
    }

    @Override
    public MeasureListDto getMeasureListByDate(MeasureByDateDto measureByDateDto) {
        //Date dateFrom = Date.from( Instant.parse( "2013-07-06T10:39:40Z" ));
        //Date dateTo = Date.from( Instant.parse( "2013-07-08T10:39:40Z" ));
        Station station = stationRepository.findByStationId(measureByDateDto.getStationId());
        if(!station.getVisible()){ //if private station verify token
            User user = userRepository.findByToken(measureByDateDto.getToken());
            if(user==null){
                return null;
            }
        }

        //String userId=verifyApiKey(measureByDateDto.getToken());
        if(verifyStationId(measureByDateDto.getStationId())) {
            Date dateFrom = Date.from(Instant.parse(measureByDateDto.getDateFrom()));
            Date dateTo = Date.from(Instant.parse(measureByDateDto.getDateTo()));

            String stationId = measureByDateDto.getStationId();
            //TODO FIX or fixed already? Check
            List<Measure> measureList = measureRepository.findByDateBetween(dateFrom, dateTo, stationId);

            //System.out.println(measureList);
            //return measureList;
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
                //System.out.println(elevation);
                return elevation;
            }
        }
        catch (Exception e){
            System.out.println("Error parsing JSON");//incase remote server fails use average elevation
        }

        return elevation;
    }

    @Override
    public ChartListDto getMeasuresForChart(MeasureByDateChartDto measureByDateChartDto) {//add switch for multiple type of charts

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
                                    measureList=measureRepository.findAvgTempByDateBeetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "humidity":{
                                    measureList=measureRepository.findAvgHumidityByDateBeetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pressure":{
                                    Float elevation = getElevation(station.getLat(), station.getLng());
                                    measureList=measureRepository.findAvgPressureByDateBeetweenGroupByHour(dateFrom, dateTo, stationId, timezone, elevation);
                                    break;
                                }
                                case "pm10":{
                                    measureList=measureRepository.findAvgPM10ByDateBeetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25":{
                                    measureList=measureRepository.findAvgPM25ByDateBeetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25Corr":{
                                    measureList=measureRepository.findAvgPM25CorrByDateBeetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }


                            }
                            break;
                        }
                        case "min":{
                            switch(measureByDateChartDto.getChartValue()){
                                case "temp":{
                                    measureList=measureRepository.findMinTempByDateBeetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "humidity":{
                                    measureList=measureRepository.findMinHumidityByDateBeetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pressure":{
                                    Float elevation = getElevation(station.getLat(), station.getLng());
                                    measureList=measureRepository.findMinPressureByDateBeetweenGroupByHour(dateFrom, dateTo, stationId, timezone, elevation);
                                    break;
                                }
                                case "pm10":{
                                    measureList=measureRepository.findMinPM10ByDateBeetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25":{
                                    measureList=measureRepository.findMinPM25ByDateBeetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25Corr":{
                                    measureList=measureRepository.findMinPM25CorrByDateBeetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                            }
                            break;
                        }
                        case "max": {
                            switch(measureByDateChartDto.getChartValue()){
                                case "temp":{
                                    measureList=measureRepository.findMaxTempByDateBeetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "humidity":{
                                    measureList=measureRepository.findMaxHumidityByDateBeetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pressure":{
                                    Float elevation = getElevation(station.getLat(), station.getLng());
                                    measureList=measureRepository.findMaxPressureByDateBeetweenGroupByHour(dateFrom, dateTo, stationId, timezone, elevation);
                                    break;
                                }
                                case "pm10":{
                                    measureList=measureRepository.findMaxPM10ByDateBeetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25":{
                                    measureList=measureRepository.findMaxPM25ByDateBeetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25Corr":{
                                    measureList=measureRepository.findMaxPM25CorrByDateBeetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
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
                                    measureList=measureRepository.findAvgTempByDateBeetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "humidity":{
                                    measureList=measureRepository.findAvgHumidityByDateBeetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pressure":{
                                    Float elevation = getElevation(station.getLat(), station.getLng());
                                    measureList=measureRepository.findAvgPressureByDateBeetweenGroupByDay(dateFrom, dateTo, stationId, timezone, elevation);
                                    break;
                                }
                                case "pm10":{
                                    measureList=measureRepository.findAvgPM10ByDateBeetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25":{
                                    measureList=measureRepository.findAvgPM25ByDateBeetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25Corr":{
                                    measureList=measureRepository.findAvgPM25CorrByDateBeetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }


                            }
                            break;
                        }
                        case "min":{
                            switch(measureByDateChartDto.getChartValue()){
                                case "temp":{
                                    measureList=measureRepository.findMinTempByDateBeetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "humidity":{
                                    measureList=measureRepository.findMinHumidityByDateBeetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pressure":{
                                    Float elevation = getElevation(station.getLat(), station.getLng());
                                    measureList=measureRepository.findMinPressureByDateBeetweenGroupByDay(dateFrom, dateTo, stationId, timezone, elevation);
                                    break;
                                }
                                case "pm10":{
                                    measureList=measureRepository.findMinPM10ByDateBeetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25":{
                                    measureList=measureRepository.findMinPM25ByDateBeetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25Corr":{
                                    measureList=measureRepository.findMinPM25CorrByDateBeetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                            }
                            break;
                        }
                        case "max": {
                            switch(measureByDateChartDto.getChartValue()){
                                case "temp":{
                                    measureList=measureRepository.findMaxTempByDateBeetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "humidity":{
                                    measureList=measureRepository.findMaxHumidityByDateBeetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pressure":{
                                    Float elevation = getElevation(station.getLat(), station.getLng());
                                    measureList=measureRepository.findMaxPressureByDateBeetweenGroupByDay(dateFrom, dateTo, stationId, timezone, elevation);
                                    break;
                                }
                                case "pm10":{
                                    measureList=measureRepository.findMaxPM10ByDateBeetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25":{
                                    measureList=measureRepository.findMaxPM25ByDateBeetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
                                    break;
                                }
                                case "pm25Corr":{
                                    measureList=measureRepository.findMaxPM25CorrByDateBeetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
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

        //TODO check if works
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

    @Override
    public LastMeasureListDto getLastMeasureAllPrivate(String token) {
        User user = userRepository.findByToken(token);
        if(user==null){
            return null;
        }
        else{
            UserStationList userStationList = userStationListRepository.findByUserId(user.getUserId());
            List<Station> stationList = stationRepository.findAllByVisible(false);
            List<String> myStationList = userStationList.getMyStationList();
            if(myStationList==null){
                return null;
            }
            else if(myStationList.isEmpty()){
                return null;
            }


            for(String stationId : myStationList){
                stationList.removeIf(station -> !station.getStationId().equals(stationId));
            }
            HashMap<String, Measure> measureList = createMeasureList(stationList);

            return new LastMeasureListDto.Builder().measureList(measureList).build();
        }

    }

    /*@Override
    public LastMeasureListDto getLastMeasurePublicAndPrivate(String token) {
        LastMeasureListDto lastMeasureListPublicDto = getLastMeasureAllPublic();
        LastMeasureListDto lastMeasureListPrivateDto = getLastMeasureAllPrivate(token);

        ArrayList<Measure> lastMeasureList

    }*/


}
