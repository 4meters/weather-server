package com.weather.server.domain.repository;

import com.weather.server.domain.dto.measure.NewMeasureDto;
import com.weather.server.domain.entity.Measure;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MeasureRepository extends MongoRepository<Measure, String>{

    //@Query(sort= "{ id: -1 }")
    //Measure findFirst();

    Measure findFirstByStationIdOrderByDateDesc(String stationId);

    //TODO test if works after changing stationId from null to proper one
    @Query(value = "{'date':{ $gte: ?0, $lte: ?1}, 'stationId': {$eq: ?2}}", sort="{date: 1}")
    List<Measure> findByDateBetween(Date from, Date to, String stationId);
    //List<Measure> findByTempBetween(String from, String to);

    Measure findByTemp(String temp);

    //Maybe merge?
    //@Query(value = "{'date':{ $gte: ?0, $lte: ?1}, 'stationId': {$eq: ?2}}")
    //findMaxno range - useless
    List<Measure> findTopByOrderByTempDesc(Date from, Date to, String stationId);//check this one more time


    void insert(List<NewMeasureDto> measureList);
}
