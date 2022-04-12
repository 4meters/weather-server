package com.weather.server.controller;

import com.weather.server.domain.dto.station.AddStationDto;
import com.weather.server.domain.dto.station.StationListDto;
import com.weather.server.domain.dto.station.StationNameDto;
import com.weather.server.domain.dto.station.VerifyStationDto;
import com.weather.server.service.StationService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value="/api/station")
public class StationApiController {

    private StationService stationService;

    @Autowired
    public StationApiController(StationService stationService) {
        this.stationService = stationService;
    }

    /*@GetMapping(value="/get-stationlist{token}")
    public ResponseEntity<?> getStation(){
        //if api key is valid
        //stationService.getStationList()
        //add token veryfication
        return new ResponseEntity<>(new StationListDto.Builder().stationList(stationService.getStationList("")).build()
                , HttpStatus.OK);

    }*/

    @GetMapping(value="/get-public-stationlist")
    public ResponseEntity<?> getPublicStationList(){
        return new ResponseEntity<>(new StationListDto.Builder().stationList(stationService.getPublicStationList()).build()
                , HttpStatus.OK);

    }


    @GetMapping(value="/get-station-name/{stationId}")
    public ResponseEntity<?> getStationName(@PathVariable String stationId){
        StationNameDto stationNameDto = stationService.getStationName(stationId);
        return stationNameDto!=null ? new ResponseEntity<>(stationNameDto, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping(value = "/verify-station")
    public ResponseEntity<?> verifyStation(@RequestBody VerifyStationDto verifyStationDto){
        return stationService.verifyStationId(verifyStationDto) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/add-station-on-map")
    public ResponseEntity<?> addStationOnMap(@RequestBody AddStationDto addStationDto){
        return stationService.addStationOnMap(addStationDto) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    /*@PostMapping(value="/add-station")
    public ResponseEntity<?> add-station(AddStationDto addStationDto){
        return stationService.addStation ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }*/
/*
    @GetMapping(value="/last-measure")
    public ResponseEntity<NewMeasureDto> getLast(){
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
*/
}
