package com.mystical.cloud.auth.controller;


import com.alibaba.fastjson.JSON;
import com.mystical.cloud.auth.bean.SelfUserDetails;
import com.mystical.cloud.auth.signature.entity.SignedParam;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@Api(tags = "demo")
public class DemoController {

    @RequestMapping("/info")
    public  String getUserInfo(@RequestBody SignedParam signedParam){
        String data = signedParam.getData();
        SelfUserDetails selfUserDetails = JSON.parseObject(data, SelfUserDetails.class);
        System.out.println(selfUserDetails);
        return data;
    }

}
