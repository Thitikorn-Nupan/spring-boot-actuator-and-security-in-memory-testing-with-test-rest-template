package com.ttknp.understandspringbootactuator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/api")
public class TestControl {

    @GetMapping(value = "/server")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public HashMap server() {
        HashMap map = new HashMap();
        map.put("status", "200");
        map.put("message", "OK");
        map.put("data", "Hello World");
        map.put("timestamp", System.currentTimeMillis());
        return map;
    }
}
