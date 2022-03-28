package com.weather.server.service.impl;

import com.weather.server.domain.dto.chart.ChartTempDto;
import com.weather.server.domain.dto.chart.ChartTempListDto;
import com.weather.server.domain.dto.measure.LastMeasureDto;
import com.weather.server.domain.dto.measure.MeasureByDateDto;
import com.weather.server.domain.dto.measure.MeasureListDto;
import com.weather.server.domain.dto.measure.NewMeasureDto;
import com.weather.server.domain.entity.Measure;
import com.weather.server.domain.entity.Station;
import com.weather.server.domain.entity.User;
import com.weather.server.domain.mapper.NewMeasureMapper;
import com.weather.server.domain.model.ISODate;
import com.weather.server.domain.repository.MeasureRepository;
import com.weather.server.domain.repository.StationRepository;
import com.weather.server.domain.repository.UserRepository;
import com.weather.server.service.MeasureService;
import org.bson.types.Decimal128;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.time.Instant;
import java.util.*;

@Service
public class MeasureServiceImpl implements MeasureService {

    private final MeasureRepository measureRepository;
    private final UserRepository userRepository;
    private final StationRepository stationRepository;

    @Autowired
    public MeasureServiceImpl(MeasureRepository measureRepository, UserRepository userRepository, StationRepository stationRepository) {
        this.measureRepository=measureRepository;
        this.userRepository = userRepository;
        this.stationRepository = stationRepository;
    }
    /*public void test(){
        ConnectionString connectionString = new ConnectionString("mongodb+srv://admin:zaq1%40WSX@cluster0.fyl2u.mongodb.net/Cluster0?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("Cluster0");
    }*/

    //TODO verifyapikey
    @Override
    public boolean saveMeasure(NewMeasureDto newMeasureDto) {
        //sth wrong here
        //String userId=verifyApiKey(newMeasureDto.getApiKey());
       // System.out.println("TEST");
        //if(userId!=null){
            //System.out.println("User");
            //System.out.println(newMeasureDto.getStationId());
            if(verifyStationId(newMeasureDto.getStationId())) {
                //System.out.println("Saving measure");
                Measure measure = new NewMeasureMapper().mapToEntity(newMeasureDto);
                //System.out.println(measure);
                measureRepository.save(measure);
                return true;
            }
            else{
                //System.out.println("Not saving measure - check stationId");
                return false;
            }
    }


        //else{
         //   System.out.println("Else2");
          //  return false;
        //}

    @Override
    public String verifyApiKey(String apiKey) {
        User user=userRepository.findByApiKey(apiKey);
        if(user == null){
            return null;
        }
        else if(user.getApiKey().equals(apiKey)) {
            return user.getUserId();
        }
        else{
            return null;
        }
    }

    @Override  //TODO chyba już działa, testować
    public boolean verifyStationId(String stationId) {
        Station station=stationRepository.findByStationId(stationId);
        //System.out.println(stationRepository.findAllByVisible(true));
        //System.out.println("TEST2");
        //System.out.println(station);
        if(station == null){
            //System.out.println("if1st");
            return false;
        }
        else if(station.getStationId().equals(stationId)) {
            //System.out.println("else ifst");
            return true;

        }
        else{
            //System.out.println("elsest");
            return false;
        }
    }

    @Override
    public LastMeasureDto getLastMeasure(String stationId) {//TODO fix it java.lang.IllegalArgumentException: Cannot convert String [4.78] to target class [org.bson.types.Decimal128]
        Measure measure =measureRepository.findFirstByStationIdOrderByDateDesc(stationId);
        //Measure measure=measureRepository.findByTemp("24.37");
        System.out.println(measure);
        LastMeasureDto lastMeasureDto = new LastMeasureDto.Builder()
                .stationId(measure.stationId)
                .date(ISODate.toString(measure.date))
                .temp(measure.temp.toString())
                .humidity(measure.humidity.toString())
                .pm10(measure.pm10.toString())
                .pm25(measure.pm25.toString())
                .pm25Corr(measure.pm25Corr.toString())
                .pressure(measure.pressure.toString())
                .build();

                //new NewMeasureDto("", "", ISODate.toString(measure.date), measure.temp, measure.humidity, measure.pressure, measure.pm25, measure.pm10, measure.pm25Corr);
        return lastMeasureDto;
    }

    @Override
    public MeasureListDto getMeasureListByDate(MeasureByDateDto measureByDateDto) {
        //Date dateFrom = Date.from( Instant.parse( "2013-07-06T10:39:40Z" ));
        //Date dateTo = Date.from( Instant.parse( "2013-07-08T10:39:40Z" ));
        String userId=verifyApiKey(measureByDateDto.getApiKey());
        if(userId!=null) {
            if(verifyStationId(measureByDateDto.getStationId())) {
                Date dateFrom = Date.from(Instant.parse(measureByDateDto.getDateFrom()));
                Date dateTo = Date.from(Instant.parse(measureByDateDto.getDateTo()));

                String stationId = measureByDateDto.getStationId();
                //TODO FIX or fixed already? Check
                List<Measure> measureList = measureRepository.findByDateBetween(dateFrom, dateTo, stationId);

                //System.out.println(measureList);
                //return measureList;
                return new MeasureListDto.Builder()
                        .measureList(measureList)
                        .build();
            }
            else{
                return null;
            }
        }
        else{
            return null;
        }


    }

    @Override
    public ChartTempListDto getMeasuresForChart(MeasureByDateDto measureByDateDto) {//add switch for multiple type of charts
        //Date dateFrom = Date.from( Instant.parse( "2013-07-06T10:39:40Z" ));
        //Date dateTo = Date.from( Instant.parse( "2013-07-08T10:39:40Z" ));
        String userId=verifyApiKey(measureByDateDto.getApiKey());
        if(userId!=null) {
            if(verifyStationId(measureByDateDto.getStationId())) {
                Date dateFrom = Date.from(Instant.parse(measureByDateDto.getDateFrom()));
                Date dateTo = Date.from(Instant.parse(measureByDateDto.getDateTo()));
                String stationId = measureByDateDto.getStationId();

                //Measure measure=null;
                List<Measure> measureList=null;

                if(dateFrom.getTime() < dateTo.getTime()){
                    long dateDiff = dateTo.getTime() - dateFrom.getTime();
                    long days_difference = (dateDiff / (1000*60*60*24)) % 365;

                    if(days_difference>=0 && days_difference<=70000){

                        /*First method
                        measureList= measureRepository.findByDateBetween(dateFrom,dateTo,stationId);


                        Date startDate=measureList.get(0).date;
                        Date endDate=measureList.get(measureList.size()-1).date;

                        Date startRangeDate=startDate;
                        //Date endRangeDate=startDate;
                        //Uzwględnić strefy czasowe
                        Calendar start = Calendar.getInstance();
                        start.setTime(startDate);
                        Calendar end = Calendar.getInstance();
                        end.setTime(endDate);



                        for (Date date = start.getTime(); start.before(end); start.add(Calendar.HOUR, 1), date = start.getTime()) {

                        }
                        */

                        /*second method

                         */
                        measureList= measureRepository.findByDateBetween(dateFrom,dateTo,stationId);


                        Date startDate=measureList.get(0).date;
                        Date endDate=measureList.get(measureList.size()-1).date;

                        Date startRangeDate=startDate;

                        Calendar start = Calendar.getInstance();
                        start.setTime(startDate);

                        Calendar inter = Calendar.getInstance();
                        inter.setTime(startDate);
                        inter.add(Calendar.HOUR, 1);

                        Calendar end = Calendar.getInstance();
                        end.setTime(endDate);



                        //
                        Decimal128 maxTemp = new Decimal128(-274);
                        Date maxTempDate = new Date();

                        //

                        List<ChartTempDto> chartTempList = new ArrayList<>();

                        /*
                        for (Date dateS = start.getTime(); start.before(end) && inter.before(end);
                             start.add(Calendar.HOUR, 1), dateS = start.getTime(), inter.add(Calendar.HOUR,1)) {
                            //System.out.println("ES");
                            Date dateT = inter.getTime();
                            measureList= measureRepository.findByDateBetween(dateS,dateT,stationId);
                            //TODO maybe use collections?
                            for(Measure measure : measureList){
                                /*if(measure.temp.compareTo(maxTemp)>0){//measure.temp > maxTemp
                                    maxTemp=measure.temp;
                                    maxTempDate=measure.date;
                                }*/
                           /* }
                            chartTempList.add(new ChartTempDto(maxTempDate, maxTemp.floatValue()));
                            maxTemp=new Decimal128(-274);

                        }*/

                        measureList= measureRepository.findByDateBetween(dateFrom,dateTo,stationId);
                        for(Measure measure : measureList){
                            chartTempList.add(new ChartTempDto(measure.date, measure.temp.floatValue()));
                        }


                        return new ChartTempListDto(chartTempList);

                        //Query query = new Query();
                       //query.addCriteria(Criteria.where("age").lt(50).gt(20));
                        //List<User> users = mongoTemplate.find(query,User.class);
                        //find max every hour
                        //measure = measureRepository.findTopByOrderByOrderTempDesc(dateFrom,dateTo,stationId);
                        /*last1
                        ConnectionString connectionString = new ConnectionString("mongodb+srv://admin:zaq1%40WSX@cluster0.fyl2u.mongodb.net/Cluster0?retryWrites=true&w=majority");
                        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
                        MongoClient mongoClient = MongoClients.create(settings);
                        MongoDatabase database = mongoClient.getDatabase("Cluster0");
                        */
                        /*last2
                        Document document = new Document("data","-1");
                        Sort sort = Sort.by(Sort.Direction.DESC, "temp");
                        measureList=database.getCollection("measure").find().sort(document));
                        */
                        //measureList= measureRepository.findMaxByDateBetween(dateFrom,dateTo,stationId);
                        //public PageRequest request = new PageRequest(0, 1, Sort.by(Sort.Direction.ASC, "temp"), "approval.approvedDate"));
                    }
                }
                //if tydzien, miesiąc rok / 1-6 dni

                //List<Measure> measureList=new ArrayList<Measure>();
                //measureList.add(measure);
                //TODO FIX or fixed already? Check
                ////List<Measure> measureList = measureRepository.findByDateBetween(dateFrom, dateTo, stationId);

                //System.out.println(measureList);
                //return measureList;
                /*return new MeasureListDto.Builder()
                        .measureList(measureList)
                        .build();*/

            }
            else{
                return null;
            }
        }
        else{
            return null;
        }
        return null;
    }

    //TODO verify apikey!
    @Override
    public boolean saveMeasurePackage(List<NewMeasureDto> newMeasureDtoList) {
        /*for(NewMeasureDto newMeasureDto : measureList){
            //System.out.println(newMeasureDto);
            boolean result = saveMeasure(newMeasureDto);
            if(result==false){
                return false;
            }
        }*/

        List<Measure> measureList=new ArrayList<>();
        for(NewMeasureDto newMeasureDto : newMeasureDtoList){
            Measure measure = new NewMeasureMapper().mapToEntity(newMeasureDto);
            measureList.add(measure);
        }
        measureRepository.insert(measureList);
        //return true;
        //System.out.println(measureList);
        //System.out.println(measureList.get(0));
        return true;
    }

    @Override
    public MeasureListDto getMeasureDatabase() {
        List<Measure> measureList = measureRepository.findAll();

        //System.out.println(measureList);
        //return measureList;
        return new MeasureListDto.Builder()
                .measureList(measureList)
                .build();
    }


}
