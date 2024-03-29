package com.weather.server.controller;

import com.weather.server.domain.dto.admin.RemoveStationDto;
import com.weather.server.domain.dto.station.StationChangeNameDto;
import com.weather.server.domain.dto.station.StationSetVisibilityDto;
import com.weather.server.domain.dto.station.*;
import com.weather.server.service.StationService;
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


    @GetMapping(value="/get-public-stationlist")
    public ResponseEntity<StationListDto> getPublicStationList(){
        return new ResponseEntity<>(stationService.getPublicStationList(), HttpStatus.OK);

    }

    @GetMapping(value="/get-station-name/{stationId}")
    public ResponseEntity<?> getStationName(@PathVariable String stationId){
        StationNameDto stationNameDto = stationService.getStationName(stationId);
        return stationNameDto!=null ? new ResponseEntity<>(stationNameDto, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping(value = "/verify-station")
    public ResponseEntity<?> verifyStation(@RequestBody VerifyStationDto verifyStationDto){
        return stationService.verifyStationIdAndStationKey(verifyStationDto) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/add-station-on-map")
    public ResponseEntity<?> addStationOnMap(@RequestBody AddStationDto addStationDto){
        return stationService.addStationOnMap(addStationDto) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/mode-switch")
    public ResponseEntity<?> modeSwitchStation(@RequestBody StationModeSwitchDto stationModeSwitchDto){
        return stationService.modeSwitch(stationModeSwitchDto) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    //TODO - verify if station is in user list
    @PostMapping(value = "/set-measure-interval")
    public ResponseEntity<?> setMeasureInterval(@RequestBody StationSetMeasureIntervalDto stationSetMeasureIntervalDto){
        return stationService.setMeasureInterval(stationSetMeasureIntervalDto) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value="/get-working-mode/{stationId}") //?used by weather station measure script only?
    public ResponseEntity<?> getCurrentMode(@PathVariable String stationId){
        StationCurrentModeDto stationCurrentModeDto = stationService.getCurrentMode(stationId);
        return stationCurrentModeDto!=null ? new ResponseEntity<>(stationCurrentModeDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping(value = "/change-station-name")
    public ResponseEntity<?> changeStationName(@RequestBody StationChangeNameDto stationChangeNameDto) {
        return stationService.changeStationName(stationChangeNameDto) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/set-visibility")
    public ResponseEntity<?> setVisibility(@RequestBody StationSetVisibilityDto stationSetVisibilityDto){
        return stationService.setVisibility(stationSetVisibilityDto) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/remove-station")
    public ResponseEntity<?> removeStation(@RequestBody RemoveStationDto removeStationDto){
        return stationService.removeStation(removeStationDto);
    }

}
