package com.weather.server.service.impl;

import com.weather.server.domain.dto.LastMeasureDto;
import com.weather.server.domain.dto.MeasureByDateDto;
import com.weather.server.domain.dto.NewMeasureDto;
import com.weather.server.domain.dto.MeasureListDto;
import com.weather.server.domain.entity.Measure;
import com.weather.server.domain.entity.Station;
import com.weather.server.domain.entity.User;
import com.weather.server.domain.mapper.NewMeasureMapper;
import com.weather.server.domain.model.ISODate;
import com.weather.server.domain.repository.MeasureRepository;
import com.weather.server.domain.repository.StationRepository;
import com.weather.server.domain.repository.UserRepository;
import com.weather.server.service.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class MeasureServiceImpl implements MeasureService {

    private final MeasureRepository measureRepository;
    private final UserRepository userRepository;
    private final StationRepository stationRepository;

    @Autowired
    public MeasureServiceImpl(MeasureRepository measureRepository, UserRepository userRepository, StationRepository stationRepository) {
        this.measureRepository=measureRepository;
        this.userRepository = userRepository;
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

    @Override
    public boolean saveMeasure(NewMeasureDto newMeasureDto) {
        //sth wrong here
        String userId=verifyApiKey(newMeasureDto.getApiKey());
        if(userId!=null){
            if(verifyStationId(newMeasureDto.getStationID())) {
                Measure measure = new NewMeasureMapper().mapToEntity(newMeasureDto, userId);
                measureRepository.save(measure);
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    @Override
    public String verifyApiKey(String apiKey) {
        User user=userRepository.findByApiKey(apiKey);
        if(user == null){
            return null;
        }
        else if(user.getApiKey().equals(apiKey)) {
            return user.getUserId();
        }
        else{
            return null;
        }
    }

    @Override
    public boolean verifyStationId(String stationId) {
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
    public LastMeasureDto getLastMeasure() {
        Measure measure =measureRepository.findFirstByOrderByDateDesc();
        //Measure measure=measureRepository.findByTemp("24.37");
        System.out.println(measure);
        LastMeasureDto lastMeasureDto = new LastMeasureDto.Builder()
                .stationID(measure.stationId)
                .date(ISODate.toString(measure.date))
                .temp(measure.temp)
                .humidity(measure.humidity)
                .pm10(measure.pm10)
                .pm25(measure.pm25)
                .pm25Corr(measure.pm25Corr)
                .pressure(measure.pressure)
                .build();

                //new NewMeasureDto("", "", ISODate.toString(measure.date), measure.temp, measure.humidity, measure.pressure, measure.pm25, measure.pm10, measure.pm25Corr);
        return lastMeasureDto;
    }

    @Override
    public MeasureListDto getMeasureListByDate(MeasureByDateDto measureByDateDto) {
        //Date dateFrom = Date.from( Instant.parse( "2013-07-06T10:39:40Z" ));
        //Date dateTo = Date.from( Instant.parse( "2013-07-08T10:39:40Z" ));
        String userId=verifyApiKey(measureByDateDto.getApiKey());
        if(userId!=null) {
            Date dateFrom = Date.from( Instant.parse(measureByDateDto.getDateFrom()));
            Date dateTo = Date.from( Instant.parse(measureByDateDto.getDateTo()));
            List <Measure> measureList = measureRepository.findByDateBetween(dateFrom, dateTo, userId);

            System.out.println(measureList);
            //return measureList;
            return new MeasureListDto.Builder()
                    .measureList(measureList)
                    .build();
        }
        else{
            return null;
        }


    }


}
