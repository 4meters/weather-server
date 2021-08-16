package com.weather.server.service.impl;

import com.weather.server.domain.entity.Measure;
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
        measureRepository.save(new Measure("2012-02-05","20","999.9","4.0","4.0","2.8"));
    }

    @Override
    public void saveMeasure() {

    }
}
