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

    @PostMapping(value="/new-measure")
    public ResponseEntity<Void> newMeasure(@RequestBody NewMeasureDto newMeasureDto){
        //if station exists in db
        return measureService.saveMeasure(newMeasureDto) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PostMapping(value="/new-measure-package") //addStation id
    public ResponseEntity<Void> newMeasure(@RequestBody NewMeasurePackageDto measureList){
        return measureService.saveMeasurePackage(measureList.getMeasureList()) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value="/last-measure")
    public ResponseEntity<LastMeasureDto> getLast(@RequestParam(name="stationId") String stationId){
        return new ResponseEntity<>(measureService.getLastMeasure(stationId), HttpStatus.OK);
    }

    @GetMapping(value="/last-measure-all") //public stations
    public ResponseEntity<LastMeasureListDto> getLastAll(){
        return new ResponseEntity<>(measureService.getLastMeasureAllPublic(), HttpStatus.OK);
    }

    @GetMapping(value="/measure-by-date")//TODO remove before release
    public ResponseEntity<?> getByDate(@RequestBody MeasureByDateDto measureByDateDto){
        MeasureListDto measureListDto = measureService.getMeasureListByDate(measureByDateDto);
        if(measureListDto!=null){
            return new ResponseEntity<>(measureListDto, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @PostMapping(value="/measure-by-date-chart")
    public ResponseEntity<?> getByDateChart(@RequestBody MeasureByDateChartDto measureByDateChartDto){
        ChartListDto chartTempListDto = measureService.getMeasuresForChart(measureByDateChartDto);
        if(chartTempListDto!=null){
            return new ResponseEntity<>(chartTempListDto, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    //TODO -TEST ONLY! REMOVE BEFORE RELEASE
    @GetMapping(value="/dump-all-db")
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
