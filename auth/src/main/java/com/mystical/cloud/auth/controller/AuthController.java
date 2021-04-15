package com.mystical.cloud.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.cj.util.StringUtils;
import com.mystical.cloud.auth.bean.UserInfo;
import com.mystical.cloud.auth.exception.EventBaseException;
import com.mystical.cloud.auth.mapper.UserMapper;

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

    /**
     * 验证token
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String auth(String token) {
        log.debug("token:[{}]", token);
        String useranme = JwtTokenUtil.parseToken(token);
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", useranme);
        UserInfo userInfo = userMapper.selectOne(queryWrapper);
        if (userInfo != null) {
            return "success";
        }
        return "未知账户";

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody UserInfo userInfo, HttpServletResponse response) {
        if (loginService.login(userInfo, response)) {
            return "登录成功";
        }
        return "登录失败";
    }

    @PostMapping("/register")
    public String register(@RequestBody UserInfo userInfo, HttpServletResponse response) {
        try {
            String username = userInfo.getUsername();
            String password = userInfo.getPassword();
            log.info("username=[{}], password=:[{}]", username, password);
            boolean succeed;
            if (StringUtils.isNullOrEmpty(username) || username.length() > 32 || StringUtils.isNullOrEmpty(password)) {
                return "填写错误";
            } else {
                QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("username", username).eq("password", password);
                Integer integer = userMapper.selectCount(queryWrapper);
                if (integer > 0) {
                    return "账户已存在";
                } else {
                    succeed = loginService.saveAndValidate(userInfo);
                }
            }
            if (succeed) {
                String token = loginService.getRegisterToken(username, 0, response);
                return token;
            } else {
                return "生成token失败，请重试";
            }
        } catch (Exception e) {
           throw new EventBaseException("内部错误");
        }
    }
}
