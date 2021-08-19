package com.weather.server.service.impl;

import com.weather.server.domain.dto.MeasureDto;
import com.weather.server.domain.entity.Measure;
import com.weather.server.domain.mapper.MeasureMapper;
import com.weather.server.domain.repository.MeasureRepository;
import com.weather.server.service.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasureServiceImpl implements MeasureService {

    private final MeasureRepository measureRepository;

    @Autowired
    public MeasureServiceImpl(MeasureRepository measureRepository) {
        this.measureRepository=measureRepository;
    }
    /*public void test(){
        ConnectionString connectionString = new ConnectionString("mongodb+srv://admin:zaq1%40WSX@cluster0.fyl2u.mongodb.net/Cluster0?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("Cluster0");
    }*/
    public void test2(){
        measureRepository.save(new Measure("2012-02-05","20","60.8","999.9","4.0","4.0","2.8"));
    }

    @Override
    public boolean saveMeasure(MeasureDto measureDto) {
        if(verifyApiKey(measureDto.getApiKey())){
            Measure measure = new MeasureMapper().mapToEntity(measureDto);
            measureRepository.save(measure);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean verifyApiKey(String apiKey) {
        //TODO set api key for user (generate) and check in user database
        String setApiKey="abc123456";
        return setApiKey.equals(apiKey);
    }


}
