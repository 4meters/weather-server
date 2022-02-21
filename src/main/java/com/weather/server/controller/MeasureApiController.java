package com.weather.server.controller;

import com.weather.server.domain.dto.*;
import com.weather.server.service.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<LastMeasureDto> getLast(){
        return new ResponseEntity<>(measureService.getLastMeasure(), HttpStatus.OK);
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
