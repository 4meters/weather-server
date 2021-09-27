package com.weather.server.controller;

import com.weather.server.domain.dto.MeasureByDateDto;
import com.weather.server.domain.dto.MeasureDto;
import com.weather.server.domain.dto.MeasureListDto;
import com.weather.server.service.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value="/api")
public class MeasureApiController {

    private MeasureService measureService;

    @Autowired
    public MeasureApiController(MeasureService measureService) {
        this.measureService = measureService;
    }

    @PostMapping(value="/new-measure")
    public ResponseEntity<Void> newMeasure(@RequestBody MeasureDto measureDto){
        //if api key is valid
        return measureService.saveMeasure(measureDto) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @GetMapping(value="/last-measure")
    public ResponseEntity<MeasureDto> getLast(){
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
