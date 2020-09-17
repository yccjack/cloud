package com.mystical.cloud.entrance.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MysticalYcc
 */
@RestController
@RequestMapping("process")
public class Process {
    @RequestMapping(value = "startEvent" ,  method = RequestMethod.POST)
    public String startEvent(@RequestBody String json){
        System.out.println(json);
        return "ok";
    }
}
