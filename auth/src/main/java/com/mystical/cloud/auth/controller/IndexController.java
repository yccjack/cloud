package com.mystical.cloud.auth.controller;


import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ycc
 * @time 15:37
 */
@RestController
@RequestMapping("index")
@Slf4j
@Api(tags = "主页")
public class IndexController {

    @PostMapping("/")
    @CrossOrigin
    public String index(HttpServletResponse response) {
        log.info("注册成功，跳转主页");
        return "http://localhost:63342/station/index.html";
    }
}
