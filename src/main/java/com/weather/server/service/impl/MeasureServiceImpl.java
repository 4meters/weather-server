package com.weather.server.service.impl;

import com.weather.server.domain.dto.chart.ChartListDto;
import com.weather.server.domain.dto.measure.*;
import com.weather.server.domain.entity.*;
import com.weather.server.domain.mapper.ChartDtoMapper;
import com.weather.server.domain.mapper.NewMeasureMapper;
import com.weather.server.domain.model.ISODate;
import com.weather.server.domain.repository.MeasureRepository;
import com.weather.server.domain.repository.StationRepository;
import com.weather.server.domain.repository.UserRepository;
import com.weather.server.domain.repository.UserStationListRepository;
import com.weather.server.service.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.time.Instant;
import java.util.*;

@Service
public class MeasureServiceImpl implements MeasureService {

    private final MeasureRepository measureRepository;
    private final UserRepository userRepository;
    private final UserStationListRepository userStationListRepository;
    private final StationRepository stationRepository;

    @Autowired
    public MeasureServiceImpl(MeasureRepository measureRepository, UserRepository userRepository, UserStationListRepository userStationListRepository, StationRepository stationRepository) {
        this.measureRepository=measureRepository;
        this.userRepository = userRepository;
        this.userStationListRepository = userStationListRepository;
        this.stationRepository = stationRepository;
    }
    /*public void test(){
        ConnectionString connectionString = new ConnectionString("mongodb+srv://admin:zaq1%40WSX@cluster0.fyl2u.mongodb.net/Cluster0?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("Cluster0");
    }*/

    //TODO verifyapikey
    @Override
    public boolean saveMeasure(NewMeasureDto newMeasureDto) {
        if(verifyStationId(newMeasureDto.getStationId())) {
            //System.out.println("Saving measure");
            Measure measure = new NewMeasureMapper().mapToEntity(newMeasureDto);
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
        else if(station.getStationId().equals(stationId)) {
            return true;

        }
        else{
            return false;
        }
    }

    @Override
    public LastMeasureDto getLastMeasure(String stationId) {
        Measure measure =measureRepository.findFirstByStationIdOrderByDateDesc(stationId);
        //Measure measure=measureRepository.findByTemp("24.37");
        System.out.println(measure);
        LastMeasureDto lastMeasureDto = new LastMeasureDto.Builder()
                .stationId(measure.stationId)
                .date(ISODate.toString(measure.date))
                .temp(measure.temp.toString())
                .humidity(measure.humidity.toString())
                .pm10(measure.pm10.toString())
                .pm25(measure.pm25.toString())
                .pm25Corr(measure.pm25Corr.toString())
                .pressure(measure.pressure.toString())
                .build();

                //new NewMeasureDto("", "", ISODate.toString(measure.date), measure.temp, measure.humidity, measure.pressure, measure.pm25, measure.pm10, measure.pm25Corr);
        return lastMeasureDto;
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
    public ChartListDto getMeasuresForChart(MeasureByDateChartDto measureByDateChartDto) {//add switch for multiple type of charts
        //Date dateFrom = Date.from( Instant.parse( "2013-07-06T10:39:40Z" ));
        //Date dateTo = Date.from( Instant.parse( "2013-07-08T10:39:40Z" ));
        //drutowanie start
        //String userId=verifyApiKey(measureByDateChartDto.getApiKey());
        //if(userId!=null) {
        //drutowanie end
        Station station = stationRepository.findByStationId(measureByDateChartDto.getStationId());
        if(!station.getVisible()){ //if private station verify token
            User user = userRepository.findByToken(measureByDateChartDto.getToken());
            if(user==null){
                return null;//403 http code?
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
                                    measureList=measureRepository.findAvgPressureByDateBeetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
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
                                    measureList=measureRepository.findMinPressureByDateBeetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
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
                                    measureList=measureRepository.findMaxPressureByDateBeetweenGroupByHour(dateFrom, dateTo, stationId, timezone);
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
                    return new ChartDtoMapper().mapToDto(measureByDateChartDto.getChartValue(),measureList);


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
                                    measureList=measureRepository.findAvgPressureByDateBeetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
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
                                    measureList=measureRepository.findMinPressureByDateBeetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
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
                                    measureList=measureRepository.findMaxPressureByDateBeetweenGroupByDay(dateFrom, dateTo, stationId, timezone);
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
                    //mapper here
                    return new ChartDtoMapper().mapToDto(measureByDateChartDto.getChartValue(),measureList);
                }
            }


        }
        else{
            return null;
        }

        return null;
    }


    //TODO verify apikey!
    @Override
    public boolean saveMeasurePackage(List<NewMeasureDto> newMeasureDtoList) {
        /*for(NewMeasureDto newMeasureDto : measureList){
            //System.out.println(newMeasureDto);
            boolean result = saveMeasure(newMeasureDto);
            if(result==false){
                return false;
            }
        }*/

        List<Measure> measureList=new ArrayList<>();
        for(NewMeasureDto newMeasureDto : newMeasureDtoList){
            Measure measure = new NewMeasureMapper().mapToEntity(newMeasureDto);
            measureList.add(measure);
        }
        measureRepository.insert(measureList);
        //return true;
        //System.out.println(measureList);
        //System.out.println(measureList.get(0));
        return true;
    }

    @Override
    public MeasureListDto getMeasureDatabase() {
        List<Measure> measureList = measureRepository.findAll();

        //System.out.println(measureList);
        //return measureList;
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
            measure =measureRepository.findFirstByStationIdOrderByDateDesc(station.getStationId());

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


}
