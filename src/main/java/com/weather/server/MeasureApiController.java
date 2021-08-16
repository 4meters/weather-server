package com.weather.server;

import com.weather.server.domain.dto.MeasureDto;
import com.weather.server.service.impl.MeasureServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value="/api")
public class MeasureApiController {

    private MeasureServiceImpl measureServiceImpl;

    /*public MeasureApiController(MeasureService measureService) {
        this.measureService = measureService;
    }*/

    public MeasureApiController(MeasureServiceImpl measureServiceImpl) {
        this.measureServiceImpl = measureServiceImpl;
    }

    @PostMapping(value="/new-measure")
    public ResponseEntity<Void> orderCheckout(@RequestBody MeasureDto measureDto){
        //if api key is valid

        measureServiceImpl.test2();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
