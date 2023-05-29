package com.weather.server.controller;

import com.weather.server.domain.dto.measure.LastMeasureDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value="/api/flyio")
public class FlyIoApiController {
    @GetMapping(value="/keep-alive")
    public ResponseEntity<Void> keepAlive(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
