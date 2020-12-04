package com.mystical.cloud.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mystical.cloud.auth.bean.SelfUserDetails;
import com.mystical.cloud.auth.bean.UserInfo;
import com.mystical.cloud.auth.mapper.UserMapper;
import com.mystical.cloud.auth.response.CommonResponse;
import com.mystical.cloud.auth.response.CommonResultEnum;
import com.mystical.cloud.auth.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResponse<String> auth(String token){
        System.out.println(token);
        String s = JwtTokenUtil.parseToken(token);
        System.out.println(s);
        if("ycc".equals(s)){
            return new CommonResponse<>(CommonResultEnum.SUCCESS);
        }
        return new CommonResponse<>(CommonResultEnum.FAILED_INSUFFICIENT_AUTHORITY);

    }

    @RequestMapping("login")
    public CommonResponse<Integer> login(@RequestBody UserInfo userInfo){
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userInfo.getUsername()).eq("password",userInfo.getPassword());
        Integer integer = userMapper.selectCount(queryWrapper);
        if(integer==1){
            return new CommonResponse<>(CommonResultEnum.SUCCESS);
        }
        return null;
    }
}
