package com.mystical.cloud.auth.controller;


import com.alibaba.fastjson.JSON;
import com.mystical.cloud.auth.bean.SelfUserDetails;
import com.mystical.cloud.auth.signature.entity.SignedParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class DemoController {

    @RequestMapping("/info")
    public  String getUserInfo(@RequestBody SignedParam signedParam){
        String data = signedParam.getData();
        SelfUserDetails selfUserDetails = JSON.parseObject(data, SelfUserDetails.class);
        System.out.println(selfUserDetails);
        return data;
    }

}
