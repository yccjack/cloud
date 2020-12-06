package com.mystical.cloud.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.cj.util.StringUtils;
import com.mystical.cloud.auth.bean.SelfUserDetails;
import com.mystical.cloud.auth.bean.UserInfo;
import com.mystical.cloud.auth.mapper.UserMapper;
import com.mystical.cloud.auth.response.CommonResponse;
import com.mystical.cloud.auth.response.CommonResultEnum;
import com.mystical.cloud.auth.service.LoginService;
import com.mystical.cloud.auth.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("auth")
@Slf4j
public class AuthController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResponse<String> auth(String token) {
        System.out.println(token);
        String s = JwtTokenUtil.parseToken(token);
        System.out.println(s);
        if ("ycc".equals(s)) {
            return new CommonResponse<>(CommonResultEnum.SUCCESS);
        }
        return new CommonResponse<>(CommonResultEnum.FAILED_INSUFFICIENT_AUTHORITY);

    }

    @RequestMapping("/login")
    public CommonResponse<Integer> login(@RequestBody UserInfo userInfo) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userInfo.getUsername()).eq("password", userInfo.getPassword());
        Integer integer = userMapper.selectCount(queryWrapper);
        if (integer == 1) {
            return new CommonResponse<>(CommonResultEnum.SUCCESS);
        }
        return null;
    }

    @PostMapping("/register")
    public CommonResponse<Object> register(@RequestBody UserInfo userInfo, HttpServletResponse response) {
        try {
            String username = userInfo.getUsername();
            String password = userInfo.getPassword();
            log.info("username=[{}], password=:[{}]",username,password);
            boolean succeed;
            if (StringUtils.isNullOrEmpty(username) || username.length() > 32 || StringUtils.isNullOrEmpty(password)) {
                return new CommonResponse<>(CommonResultEnum.REGISTER_ERROR);
            } else {
                QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("username", username).eq("password", password);
                Integer integer = userMapper.selectCount(queryWrapper);
                if (integer > 0) {
                    return new CommonResponse<>(CommonResultEnum.REGISTER_ERROR);
                } else {
                    succeed = loginService.saveAndValidate(userInfo);
                }
            }
            if (succeed) {
                String token = loginService.getRegisterToken(username,0);
               // response.sendRedirect("http://localhost:63342/station/login.html");
                return new CommonResponse<>(CommonResultEnum.SUCCESS,token);
            } else {
                return new CommonResponse<>(CommonResultEnum.SYSTEM_FAIL);
            }
        } catch (Exception e) {
            return new CommonResponse<>(CommonResultEnum.SYSTEM_FAIL);
        }
    }
}
