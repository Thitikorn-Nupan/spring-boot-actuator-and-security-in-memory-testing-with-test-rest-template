package com.ttknp.understandspringbootactuator.controller;

import com.ttknp.understandspringbootactuator.entity.Robot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// *** Note you can test api on cmd using "curl <localhost:8081/*>"
@RestController
@RequestMapping(value = "/api/robot")
public class RobotControl {

    private final Logger logger;

    public RobotControl() {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    // The @ResponseBody annotation tells Spring MVC not to render a model into a view but, rather, to write the returned object into the response body ** it will work like ResponseEntity.body(...);
    @ResponseBody
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @GetMapping
    private Robot getRobotById(@RequestParam(required = false,defaultValue = "1000") int id) {
        return new Robot(id,"XT-23565-TD6-265");
    }
}
