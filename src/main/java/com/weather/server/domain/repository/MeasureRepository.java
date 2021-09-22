package com.weather.server.domain.repository;

import com.weather.server.domain.entity.Measure;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MeasureRepository extends MongoRepository<Measure, String>{

    //@Query(sort= "{ id: -1 }")
    //Measure findFirst();

    Measure findFirstByOrderByIdDesc();
    List<Measure> findByDateBetween(Date from, Date to);
    //List<Measure> findByTempBetween(String from, String to);

    Measure findByTemp(String temp);
}
