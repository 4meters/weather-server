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
        String userId=measureDto.getApiKey();
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
        //TODO set api key for user (generate) and check in user database
        User user=userRepository.findByApiKey(apiKey);
        if(user.getApiKey().equals(apiKey)) {
            return user.getUserId();
        }
        else{
            return null;
        }
    }


}
