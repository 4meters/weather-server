package com.weather.server.domain.repository;

import com.weather.server.domain.dto.measure.NewMeasureDto;
import com.weather.server.domain.entity.Measure;
import com.weather.server.domain.entity.MeasureAggregation;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MeasureRepository extends MongoRepository<Measure, String>{


    void deleteByStationId(String stationId);

    @Aggregation(pipeline={
            "{$match: {stationId: {$eq: ?0}}}",
            "{$sort: {date: -1}}",
            "{$limit: 1}",
            "{$addFields: " +
                    "{pressure: {$round: [{$multiply: ['$pressure',{$pow: [{$subtract: [1, {$divide: [{$multiply: [0.0065, ?1]}," +
                    " {$sum: ['$temp', {$multiply: [0.0065, ?1]}, 273.15]}]}]}, -5.257]}]}, 0]}}}",
            "{$addFields: {temp: { $round: ['$temp', 1] }," +
                    "humidity: { $round: ['$humidity', 0] }," +
                    "pm25: { $round: ['$pm25', 1] }," +
                    "pm25Corr: { $round: ['$pm25Corr', 1] }," +
                    "pm10: { $round: ['$pm10', 1] }}}"
    })
    Measure findLastMeasureByStationId(String stationId, float elevation);

    @Query(value = "{'date':{ $gte: ?0, $lte: ?1}, 'stationId': {$eq: ?2}}", sort="{date: 1}")
    List<Measure> findByDateBetween(Date from, Date to, String stationId);




    //Aggregations: max by hour
    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},temp: { $max: '$temp'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', temp: { $round: ['$temp', 1] }}}"
    })
    List<MeasureAggregation> findMaxTempByDateBetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},humidity: { $max: '$humidity'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', humidity: { $round: ['$humidity', 0]}}}"
    })
    List<MeasureAggregation> findMaxHumidityByDateBetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},pressure: { $max: '$pressure'}," +
                    " temp: {$avg: '$temp'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: " +
                    "{date: '$_id'," +
                    "pressure: {$round: [{$multiply: ['$pressure',{$pow: [{$subtract: [1, {$divide: [{$multiply: [0.0065, ?4]}," +
                    " {$sum: ['$temp', {$multiply: [0.0065, ?4]}, 273.15]}]}]}, -5.257]}]}, 0]}}}"
    })
    List<MeasureAggregation> findMaxPressureByDateBetweenGroupByHour(Date from, Date to, String stationId, String timezone, float elevation);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},pm10: { $max: '$pm10'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm10: { $round: ['$pm10', 1]}}}"
    })
    List<MeasureAggregation> findMaxPM10ByDateBetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},pm25: { $max: '$pm25'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25: { $round: ['$pm25', 1]}}}"
    })
    List<MeasureAggregation> findMaxPM25ByDateBetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},pm25Corr: { $max: '$pm25Corr'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25Corr: { $round: ['$pm25Corr', 1]}}}"
    })
    List<MeasureAggregation> findMaxPM25CorrByDateBetweenGroupByHour(Date from, Date to, String stationId, String timezone);
    //Aggregations: max by hour - end




    //Aggregations: max by day

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},temp: { $max: '$temp'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', temp: { $round: ['$temp', 1] }}}"
    })
    List<MeasureAggregation> findMaxTempByDateBetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},humidity: { $max: '$humidity'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', humidity: { $round: ['$humidity', 0]}}}"
    })
    List<MeasureAggregation> findMaxHumidityByDateBetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pressure: { $max: '$pressure'}, temp: {$avg: '$temp'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: " +
                    "{date: '$_id'," +
                    "pressure: {$round: [{$multiply: ['$pressure',{$pow: [{$subtract: [1, {$divide: [{$multiply: [0.0065, ?4]}," +
                    " {$sum: ['$temp', {$multiply: [0.0065, ?4]}, 273.15]}]}]}, -5.257]}]}, 0]}}}"
    })
    List<MeasureAggregation> findMaxPressureByDateBetweenGroupByDay(Date from, Date to, String stationId, String timezone, float elevation);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pm10: { $max: '$pm10'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm10: { $round: ['$pm10', 1]}}}"
    })
    List<MeasureAggregation> findMaxPM10ByDateBetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pm25: { $max: '$pm25'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25: { $round: ['$pm25', 1]}}}"
    })
    List<MeasureAggregation> findMaxPM25ByDateBetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pm25Corr: { $max: '$pm25Corr'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25Corr: { $round: ['$pm25Corr', 1]}}}"
    })
    List<MeasureAggregation> findMaxPM25CorrByDateBetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    //Aggregations max by day - end





    //Aggregations min by hour

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},temp: { $min: '$temp'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', temp: { $round: ['$temp', 1] }}}"
    })
    List<MeasureAggregation> findMinTempByDateBetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},humidity: { $min: '$humidity'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', humidity: { $round: ['$humidity', 0]}}}"
    })
    List<MeasureAggregation> findMinHumidityByDateBetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},pressure: { $min: '$pressure'}, temp: {$avg: '$temp'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: " +
                    "{date: '$_id'," +
                    "pressure: {$round: [{$multiply: ['$pressure',{$pow: [{$subtract: [1, {$divide: [{$multiply: [0.0065, ?4]}," +
                    " {$sum: ['$temp', {$multiply: [0.0065, ?4]}, 273.15]}]}]}, -5.257]}]}, 0]}}}"
    })
    List<MeasureAggregation> findMinPressureByDateBetweenGroupByHour(Date from, Date to, String stationId, String timezone, float elevation);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},pm10: { $min: '$pm10'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm10: { $round: ['$pm10', 1]}}}"
    })
    List<MeasureAggregation> findMinPM10ByDateBetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},pm25: { $min: '$pm25'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25: { $round: ['$pm25', 1]}}}"
    })
    List<MeasureAggregation> findMinPM25ByDateBetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},pm25Corr: { $min: '$pm25Corr'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25Corr: { $round: ['$pm25Corr', 1]}}}"
    })
    List<MeasureAggregation> findMinPM25CorrByDateBetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    //Aggregations min by hour end

    //Aggregation min by day
    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},temp: { $min: '$temp'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', temp: { $round: ['$temp', 1] }}}"
    })
    List<MeasureAggregation> findMinTempByDateBetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},humidity: { $min: '$humidity'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', humidity: { $round: ['$humidity', 0]}}}"
    })
    List<MeasureAggregation> findMinHumidityByDateBetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pressure: { $min: '$pressure'}, temp: {$avg: '$temp'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: " +
                    "{date: '$_id'," +
                    "pressure: {$round: [{$multiply: ['$pressure',{$pow: [{$subtract: [1, {$divide: [{$multiply: [0.0065, ?4]}," +
                    " {$sum: ['$temp', {$multiply: [0.0065, ?4]}, 273.15]}]}]}, -5.257]}]}, 0]}}}"
    })
    List<MeasureAggregation> findMinPressureByDateBetweenGroupByDay(Date from, Date to, String stationId, String timezone, float elevation);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pm10: { $min: '$pm10'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm10: { $round: ['$pm10', 1]}}}"
    })
    List<MeasureAggregation> findMinPM10ByDateBetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pm25: { $min: '$pm25'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25: { $round: ['$pm25', 1]}}}"
    })
    List<MeasureAggregation> findMinPM25ByDateBetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pm25Corr: { $min: '$pm25Corr'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25Corr: { $round: ['$pm25Corr', 1]}}}"
    })
    List<MeasureAggregation> findMinPM25CorrByDateBetweenGroupByDay(Date from, Date to, String stationId, String timezone);
    //Aggregation min by day end

    //Aggregation avg

    //Aggregations avg by hour

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},temp: { $avg: '$temp'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', temp: { $round: ['$temp', 1] }}}"
    })
    List<MeasureAggregation> findAvgTempByDateBetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},humidity: { $avg: '$humidity'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', humidity: { $round: ['$humidity', 0]}}}"
    })
    List<MeasureAggregation> findAvgHumidityByDateBetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},pressure: { $avg: '$pressure'}, temp: {$avg: '$temp'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: " +
                    "{date: '$_id'," +
                    "pressure: {$round: [{$multiply: ['$pressure',{$pow: [{$subtract: [1, {$divide: [{$multiply: [0.0065, ?4]}," +
                    " {$sum: ['$temp', {$multiply: [0.0065, ?4]}, 273.15]}]}]}, -5.257]}]}, 0]}}}"
    })
    List<MeasureAggregation> findAvgPressureByDateBetweenGroupByHour(Date from, Date to, String stationId, String timezone, float elevation);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},pm10: { $avg: '$pm10'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm10: { $round: ['$pm10', 1]}}}"
    })
    List<MeasureAggregation> findAvgPM10ByDateBetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},pm25: { $avg: '$pm25'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25: { $round: ['$pm25', 1]}}}"
    })
    List<MeasureAggregation> findAvgPM25ByDateBetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},pm25Corr: { $avg: '$pm25Corr'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25Corr: { $round: ['$pm25Corr', 1]}}}"
    })
    List<MeasureAggregation> findAvgPM25CorrByDateBetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    //Aggregations avg by hour end

    //Aggregation avg by day
    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},temp: { $avg: '$temp'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', temp: { $round: ['$temp', 1] }}}"
    })
    List<MeasureAggregation> findAvgTempByDateBetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},humidity: { $avg: '$humidity'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', humidity: { $round: ['$humidity', 0]}}}"
    })
    List<MeasureAggregation> findAvgHumidityByDateBetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pressure: { $avg: '$pressure'}, temp: {$avg: '$temp'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: " +
                    "{date: '$_id'," +
                    "pressure: {$round: [{$multiply: ['$pressure',{$pow: [{$subtract: [1, {$divide: [{$multiply: [0.0065, ?4]}," +
                    " {$sum: ['$temp', {$multiply: [0.0065, ?4]}, 273.15]}]}]}, -5.257]}]}, 0]}}}"
    })
    List<MeasureAggregation> findAvgPressureByDateBetweenGroupByDay(Date from, Date to, String stationId, String timezone, float elevation);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pm10: { $avg: '$pm10'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm10: { $round: ['$pm10', 1]}}}"
    })
    List<MeasureAggregation> findAvgPM10ByDateBetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pm25: { $avg: '$pm25'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25: { $round: ['$pm25', 1]}}}"
    })
    List<MeasureAggregation> findAvgPM25ByDateBetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pm25Corr: { $avg: '$pm25Corr'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25Corr: { $round: ['$pm25Corr', 1]}}}"
    })
    List<MeasureAggregation> findAvgPM25CorrByDateBetweenGroupByDay(Date from, Date to, String stationId, String timezone);
    //Aggregation avg by day end

    //Aggregation avg -end

    void insert(List<NewMeasureDto> measureList);
}
