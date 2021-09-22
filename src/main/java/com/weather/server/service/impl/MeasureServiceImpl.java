package com.weather.server.service.impl;

import com.weather.server.domain.dto.MeasureDto;
import com.weather.server.domain.entity.Measure;
import com.weather.server.domain.entity.User;
import com.weather.server.domain.mapper.MeasureMapper;
import com.weather.server.domain.repository.MeasureRepository;
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

    @Autowired
    public MeasureServiceImpl(MeasureRepository measureRepository, UserRepository userRepository) {
        this.measureRepository=measureRepository;
        this.userRepository = userRepository;
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
    public boolean saveMeasure(MeasureDto measureDto) {
        //sth wrong here
        String userId=verifyApiKey(measureDto.getApiKey());
        if(userId!=null){
            Measure measure = new MeasureMapper().mapToEntity(measureDto, userId);
            measureRepository.save(measure);
            return true;
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
    public MeasureDto getLastMeasure() {
        Measure measure =measureRepository.findFirstByOrderByIdDesc();
        //Measure measure=measureRepository.findByTemp("24.37");
        System.out.println(measure);
        MeasureDto measureDto = new MeasureDto("",measure.timestamp, measure.temp, measure.humidity, measure.pressure, measure.pm25, measure.pm10, measure.pm25Corr);
        return measureDto;
    }

    @Override
    public void getMeasureListByDate() {
        Date dateFrom = Date.from( Instant.parse( "2012-07-06T10:39:40Z" ));
        Date dateTo = Date.from( Instant.parse( "2014-07-08T10:39:40Z" ));
        List <Measure> measureList = measureRepository.findByDateBetween(dateFrom,dateTo);
        System.out.println(measureList);
        //return measureList;
    }


}
