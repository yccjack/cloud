package com.mystical.cloud.entrance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MysticalYcc
 */
@RestController
@RequestMapping("process")
public class Process {
    @RequestMapping("startEvent")
    public String startEvent(){

        return "ok";
    }
}
