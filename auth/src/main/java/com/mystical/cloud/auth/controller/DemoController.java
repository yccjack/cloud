package com.mystical.cloud.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class DemoController {

    @RequestMapping("/info")
    public  String getUserInfo(){

        return "info";
    }
}
