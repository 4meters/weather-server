package com.weather.server.controller;

import com.weather.server.domain.dto.chart.ChartListDto;
import com.weather.server.domain.dto.measure.*;
import com.weather.server.service.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value="/api/measure")
public class MeasureApiController {

    private MeasureService measureService;

    @Autowired
    public MeasureApiController(MeasureService measureService) {
        this.measureService = measureService;
    }

    @PostMapping(value="/new-measure") //addStation id //TODO add checking if stationId exists in database and its assigned to user maybe?
    public ResponseEntity<Void> newMeasure(@RequestBody NewMeasureDto newMeasureDto){
        //if api key is valid
        return measureService.saveMeasure(newMeasureDto) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PostMapping(value="/new-measure-package") //addStation id
    public ResponseEntity<Void> newMeasure(@RequestBody NewMeasurePackageDto measureList){
        //if api key is valid
        return measureService.saveMeasurePackage(measureList.getMeasureList()) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value="/last-measure") //add stationID
    public ResponseEntity<LastMeasureDto> getLast(@RequestParam String stationId){
        return new ResponseEntity<>(measureService.getLastMeasure(stationId), HttpStatus.OK);
    }

    @GetMapping(value="/last-measure-all") //add stationID
    public ResponseEntity<LastMeasureListDto> getLastAll(){
        return new ResponseEntity<>(measureService.getLastMeasureAllPublic(), HttpStatus.OK);
    }

    @GetMapping(value="/measure-by-date") //TODO added, test later
    public ResponseEntity<?> getByDate(@RequestBody MeasureByDateDto measureByDateDto){
        MeasureListDto measureListDto = measureService.getMeasureListByDate(measureByDateDto);
        if(measureListDto!=null){
            return new ResponseEntity<>(measureListDto, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @PostMapping(value="/measure-by-date-chart") //TODO added, test later
    public ResponseEntity<?> getByDateChart(@RequestBody MeasureByDateChartDto measureByDateChartDto){
        ChartListDto chartTempListDto = measureService.getMeasuresForChart(measureByDateChartDto);
        if(chartTempListDto!=null){
            return new ResponseEntity<>(chartTempListDto, HttpStatus.OK);
        }//TODO maybe change apiKey to token and verify if user station is private if yes than verify token, verify not succesful -> 403
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    //TODO -TEST ONLY! REMOVE BEFORE RELEASE
    @GetMapping(value="/dump-all-db") //add station ID
    public ResponseEntity<?> getByDate(){
        MeasureListDto measureListDto = measureService.getMeasureDatabase();
        if(measureListDto!=null){
            return new ResponseEntity<>(measureListDto, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }
    //TODO END
}
