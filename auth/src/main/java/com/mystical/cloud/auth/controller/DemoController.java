package com.mystical.cloud.auth.controller;


import com.alibaba.fastjson.JSON;
import com.mystical.cloud.auth.bean.SelfUserDetails;
import com.mystical.cloud.auth.service.AffairTestService;
import com.mystical.cloud.auth.signature.entity.SignedParam;
import io.swagger.annotations.Api;
import net.sf.jsqlparser.expression.CastExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@Api(tags = "demo")
public class DemoController {

    @Autowired
    AffairTestService affairTestService;
    @RequestMapping("/info")
    public  String getUserInfo(@RequestBody SignedParam signedParam){
        String data = signedParam.getData();
        SelfUserDetails selfUserDetails = JSON.parseObject(data, SelfUserDetails.class);
        System.out.println(selfUserDetails);
        return data;
    }

    @GetMapping("affair")
    public String testAffairBug(){
        return affairTestService.testAffair();

    }


}
