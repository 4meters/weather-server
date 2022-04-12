package com.weather.server.domain.repository;

import com.weather.server.domain.entity.UserStationList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStationListRepository extends MongoRepository<UserStationList, String> {

    UserStationList findByUserId(String userId);

}
