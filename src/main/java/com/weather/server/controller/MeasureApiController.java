package com.weather.server.controller;

import com.weather.server.domain.dto.chart.ChartTempListDto;
import com.weather.server.domain.dto.measure.*;
import com.weather.server.service.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

    @PostMapping(value="/new-measure") //addStation id
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
    public ResponseEntity<?> getByDateChart(@RequestBody MeasureByDateDto measureByDateDto){
        ChartTempListDto chartTempListDto = measureService.getMeasuresForChart(measureByDateDto);
        if(chartTempListDto!=null){
            return new ResponseEntity<>(chartTempListDto, HttpStatus.OK);
        }
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
