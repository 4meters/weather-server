package com.weather.server.controller;

import com.weather.server.domain.dto.LastMeasureDto;
import com.weather.server.domain.dto.MeasureByDateDto;
import com.weather.server.domain.dto.NewMeasureDto;
import com.weather.server.domain.dto.MeasureListDto;
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
        //if api key is valid
        return measureService.saveMeasure(newMeasureDto) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @GetMapping(value="/last-measure")
    public ResponseEntity<LastMeasureDto> getLast(){
        return new ResponseEntity<>(measureService.getLastMeasure(), HttpStatus.OK);
    }

    @GetMapping(value="/measure-by-date")
    public ResponseEntity<?> getByDate(@RequestBody MeasureByDateDto measureByDateDto){
        MeasureListDto measureListDto = measureService.getMeasureListByDate(measureByDateDto);
        if(measureListDto!=null){
            return new ResponseEntity<>(measureListDto, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

}
