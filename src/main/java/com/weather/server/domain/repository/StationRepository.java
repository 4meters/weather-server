package com.weather.server.domain.repository;

import com.weather.server.domain.entity.Station;
import com.weather.server.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends MongoRepository<User, String> {
    Station findByStationID(String stationID);

}
