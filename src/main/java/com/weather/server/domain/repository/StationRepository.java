package com.weather.server.domain.repository;

import com.weather.server.domain.entity.Measure;
import com.weather.server.domain.entity.Station;
import com.weather.server.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StationRepository extends MongoRepository<Station, String> {

    Station findByStationId(String stationId);

    List<Station> findByStationIdIn(List<String> stationId);

    void deleteByStationId(String stationId);

    List<Station> findAllByVisible(Boolean visible);

}
