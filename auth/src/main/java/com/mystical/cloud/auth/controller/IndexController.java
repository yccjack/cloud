package com.mystical.cloud.auth.controller;

import com.mystical.cloud.auth.response.CommonResponse;
import com.mystical.cloud.auth.response.CommonResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ycc
 * @time 15:37
 */
@RestController
@RequestMapping("index")
@Slf4j
public class IndexController {

    @PostMapping("/")
    @CrossOrigin
    public CommonResponse<Object> index(HttpServletResponse response) {
        log.info("注册成功，跳转主页");
        return new CommonResponse<>(CommonResultEnum.SUCCESS, "http://localhost:63342/station/index.html");
    }
}
