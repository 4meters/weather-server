package com.weather.server.domain.repository;

import com.weather.server.domain.entity.Measure;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasureRepository extends MongoRepository<Measure, String> {

}
