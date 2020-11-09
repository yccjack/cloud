package com.mystical.cloud.auth.controller;

import com.mystical.cloud.auth.response.CommonResponse;
import com.mystical.cloud.auth.response.CommonResultEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @RequestMapping("/info")
    public CommonResponse<String> auth(String token){
        return new CommonResponse<>(CommonResultEnum.SUCCESS);
    }

}
