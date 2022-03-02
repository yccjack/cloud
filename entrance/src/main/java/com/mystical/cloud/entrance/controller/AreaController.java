package com.mystical.cloud.entrance.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/area")
@RestController
public class AreaController {

    @RequestMapping("/city/{areaId}")
    public String getCity(@PathVariable("areaId") String id) {

        return null;

    }
}
