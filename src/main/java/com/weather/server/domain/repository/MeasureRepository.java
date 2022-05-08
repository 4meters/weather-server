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


    @Aggregation(pipeline={
            "{$match: {stationId: {$eq: ?0}}}",
            "{$sort: {date: -1}}",
            "{$limit: 1}",
            "{$addFields: " +
                    "{pressure: {$round: [{$multiply: ['$pressure',{$pow: [{$subtract: [1, {$divide: [{$multiply: [0.0065, ?1]}," +
                    " {$sum: ['$temp', {$multiply: [0.0065, ?1]}, 273.15]}]}]}, -5.257]}]}, 0]}}}"
    })
    Measure findLastMeasureByStationId(String stationId, float elevation);

    //TODO test if works after changing stationId from null to proper one
    @Query(value = "{'date':{ $gte: ?0, $lte: ?1}, 'stationId': {$eq: ?2}}", sort="{date: 1}")
    List<Measure> findByDateBetween(Date from, Date to, String stationId);




    //Aggregations: max by hour
    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},temp: { $max: '$temp'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', temp: { $round: ['$temp', 1] }}}"
    })
    List<MeasureAggregation> findMaxTempByDateBeetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},humidity: { $max: '$humidity'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', humidity: { $round: ['$humidity', 0]}}}"
    })
    List<MeasureAggregation> findMaxHumidityByDateBeetweenGroupByHour(Date from, Date to, String stationId, String timezone);

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
    List<MeasureAggregation> findMaxPressureByDateBeetweenGroupByHour(Date from, Date to, String stationId, String timezone, float elevation);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},pm10: { $max: '$pm10'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm10: { $round: ['$pm10', 1]}}}"
    })
    List<MeasureAggregation> findMaxPM10ByDateBeetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},pm25: { $max: '$pm25'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25: { $round: ['$pm25', 1]}}}"
    })
    List<MeasureAggregation> findMaxPM25ByDateBeetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},pm25Corr: { $max: '$pm25Corr'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25Corr: { $round: ['$pm25Corr', 1]}}}"
    })
    List<MeasureAggregation> findMaxPM25CorrByDateBeetweenGroupByHour(Date from, Date to, String stationId, String timezone);
    //Aggregations: max by hour - end




    //Aggregations: max by day

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},temp: { $max: '$temp'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', temp: { $round: ['$temp', 1] }}}"
    })
    List<MeasureAggregation> findMaxTempByDateBeetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},humidity: { $max: '$humidity'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', humidity: { $round: ['$humidity', 0]}}}"
    })
    List<MeasureAggregation> findMaxHumidityByDateBeetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pressure: { $max: '$pressure'}, temp: {$avg: '$temp'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: " +
                    "{date: '$_id'," +
                    "pressure: {$round: [{$multiply: ['$pressure',{$pow: [{$subtract: [1, {$divide: [{$multiply: [0.0065, ?4]}," +
                    " {$sum: ['$temp', {$multiply: [0.0065, ?4]}, 273.15]}]}]}, -5.257]}]}, 0]}}}"
    })
    List<MeasureAggregation> findMaxPressureByDateBeetweenGroupByDay(Date from, Date to, String stationId, String timezone, float elevation);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pm10: { $max: '$pm10'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm10: { $round: ['$pm10', 1]}}}"
    })
    List<MeasureAggregation> findMaxPM10ByDateBeetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pm25: { $max: '$pm25'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25: { $round: ['$pm25', 1]}}}"
    })
    List<MeasureAggregation> findMaxPM25ByDateBeetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pm25Corr: { $max: '$pm25Corr'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25Corr: { $round: ['$pm25Corr', 1]}}}"
    })
    List<MeasureAggregation> findMaxPM25CorrByDateBeetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    //Aggregations max by day - end





    //Aggregations min by hour

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},temp: { $min: '$temp'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', temp: { $round: ['$temp', 1] }}}"
    })
    List<MeasureAggregation> findMinTempByDateBeetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},humidity: { $min: '$humidity'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', humidity: { $round: ['$humidity', 0]}}}"
    })
    List<MeasureAggregation> findMinHumidityByDateBeetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},pressure: { $min: '$pressure'}, temp: {$avg: '$temp'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: " +
                    "{date: '$_id'," +
                    "pressure: {$round: [{$multiply: ['$pressure',{$pow: [{$subtract: [1, {$divide: [{$multiply: [0.0065, ?4]}," +
                    " {$sum: ['$temp', {$multiply: [0.0065, ?4]}, 273.15]}]}]}, -5.257]}]}, 0]}}}"
    })
    List<MeasureAggregation> findMinPressureByDateBeetweenGroupByHour(Date from, Date to, String stationId, String timezone, float elevation);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},pm10: { $min: '$pm10'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm10: { $round: ['$pm10', 1]}}}"
    })
    List<MeasureAggregation> findMinPM10ByDateBeetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},pm25: { $min: '$pm25'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25: { $round: ['$pm25', 1]}}}"
    })
    List<MeasureAggregation> findMinPM25ByDateBeetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},pm25Corr: { $min: '$pm25Corr'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25Corr: { $round: ['$pm25Corr', 1]}}}"
    })
    List<MeasureAggregation> findMinPM25CorrByDateBeetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    //Aggregations min by hour end

    //Aggregation min by day
    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},temp: { $min: '$temp'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', temp: { $round: ['$temp', 1] }}}"
    })
    List<MeasureAggregation> findMinTempByDateBeetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},humidity: { $min: '$humidity'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', humidity: { $round: ['$humidity', 0]}}}"
    })
    List<MeasureAggregation> findMinHumidityByDateBeetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pressure: { $min: '$pressure'}, temp: {$avg: '$temp'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: " +
                    "{date: '$_id'," +
                    "pressure: {$round: [{$multiply: ['$pressure',{$pow: [{$subtract: [1, {$divide: [{$multiply: [0.0065, ?4]}," +
                    " {$sum: ['$temp', {$multiply: [0.0065, ?4]}, 273.15]}]}]}, -5.257]}]}, 0]}}}"
    })
    List<MeasureAggregation> findMinPressureByDateBeetweenGroupByDay(Date from, Date to, String stationId, String timezone, float elevation);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pm10: { $min: '$pm10'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm10: { $round: ['$pm10', 1]}}}"
    })
    List<MeasureAggregation> findMinPM10ByDateBeetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pm25: { $min: '$pm25'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25: { $round: ['$pm25', 1]}}}"
    })
    List<MeasureAggregation> findMinPM25ByDateBeetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pm25Corr: { $min: '$pm25Corr'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25Corr: { $round: ['$pm25Corr', 1]}}}"
    })
    List<MeasureAggregation> findMinPM25CorrByDateBeetweenGroupByDay(Date from, Date to, String stationId, String timezone);
    //Aggregation min by day end

    //Aggregation avg

    //Aggregations avg by hour

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},temp: { $avg: '$temp'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', temp: { $round: ['$temp', 1] }}}"
    })
    List<MeasureAggregation> findAvgTempByDateBeetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},humidity: { $avg: '$humidity'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', humidity: { $round: ['$humidity', 0]}}}"
    })
    List<MeasureAggregation> findAvgHumidityByDateBeetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},pressure: { $avg: '$pressure'}, temp: {$avg: '$temp'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: " +
                    "{date: '$_id'," +
                    "pressure: {$round: [{$multiply: ['$pressure',{$pow: [{$subtract: [1, {$divide: [{$multiply: [0.0065, ?4]}," +
                    " {$sum: ['$temp', {$multiply: [0.0065, ?4]}, 273.15]}]}]}, -5.257]}]}, 0]}}}"
    })
    List<MeasureAggregation> findAvgPressureByDateBeetweenGroupByHour(Date from, Date to, String stationId, String timezone, float elevation);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},pm10: { $avg: '$pm10'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm10: { $round: ['$pm10', 1]}}}"
    })
    List<MeasureAggregation> findAvgPM10ByDateBeetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},pm25: { $avg: '$pm25'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25: { $round: ['$pm25', 1]}}}"
    })
    List<MeasureAggregation> findAvgPM25ByDateBeetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d %H:00', timezone: ?3}},pm25Corr: { $avg: '$pm25Corr'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25Corr: { $round: ['$pm25Corr', 1]}}}"
    })
    List<MeasureAggregation> findAvgPM25CorrByDateBeetweenGroupByHour(Date from, Date to, String stationId, String timezone);

    //Aggregations avg by hour end

    //Aggregation avg by day
    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},temp: { $avg: '$temp'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', temp: { $round: ['$temp', 1] }}}"
    })
    List<MeasureAggregation> findAvgTempByDateBeetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},humidity: { $avg: '$humidity'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', humidity: { $round: ['$humidity', 0]}}}"
    })
    List<MeasureAggregation> findAvgHumidityByDateBeetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pressure: { $avg: '$pressure'}, temp: {$avg: '$temp'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: " +
                    "{date: '$_id'," +
                    "pressure: {$round: [{$multiply: ['$pressure',{$pow: [{$subtract: [1, {$divide: [{$multiply: [0.0065, ?4]}," +
                    " {$sum: ['$temp', {$multiply: [0.0065, ?4]}, 273.15]}]}]}, -5.257]}]}, 0]}}}"
    })
    List<MeasureAggregation> findAvgPressureByDateBeetweenGroupByDay(Date from, Date to, String stationId, String timezone, float elevation);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pm10: { $avg: '$pm10'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm10: { $round: ['$pm10', 1]}}}"
    })
    List<MeasureAggregation> findAvgPM10ByDateBeetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pm25: { $avg: '$pm25'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25: { $round: ['$pm25', 1]}}}"
    })
    List<MeasureAggregation> findAvgPM25ByDateBeetweenGroupByDay(Date from, Date to, String stationId, String timezone);

    @Aggregation(pipeline={
            "{$match: {date: { $gte: ?0, $lte: ?1}, stationId: {$eq: ?2}}}",
            "{$group: {_id: {$dateToString:{date: '$date', format: '%Y-%m-%d', timezone: ?3}},pm25Corr: { $avg: '$pm25Corr'}}}",
            "{$sort: {_id: 1}}",
            "{$addFields: {date: '$_id', pm25Corr: { $round: ['$pm25Corr', 1]}}}"
    })
    List<MeasureAggregation> findAvgPM25CorrByDateBeetweenGroupByDay(Date from, Date to, String stationId, String timezone);
    //Aggregation avg by day end

    //Aggregation avg -end

    void insert(List<NewMeasureDto> measureList);
}
