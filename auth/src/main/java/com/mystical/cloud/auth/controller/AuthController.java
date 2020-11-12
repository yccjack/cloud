package com.mystical.cloud.auth.controller;

import com.mystical.cloud.auth.response.CommonResponse;
import com.mystical.cloud.auth.response.CommonResultEnum;
import com.mystical.cloud.auth.utils.JwtTokenUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @RequestMapping("/info")
    public CommonResponse<String> auth(String token){
        System.out.println(token);
        String s = JwtTokenUtil.parseToken(token);
        System.out.println(s);
        if("ycc".equals(s)){
            return new CommonResponse<>(CommonResultEnum.SUCCESS);
        }
        return new CommonResponse<>(CommonResultEnum.FAILED_INSUFFICIENT_AUTHORITY);

    }

}
