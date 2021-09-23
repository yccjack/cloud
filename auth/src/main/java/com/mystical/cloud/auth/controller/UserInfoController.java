package com.mystical.cloud.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mystical.cloud.auth.bean.UserInfo;
import com.mystical.cloud.auth.exception.EventBaseException;
import com.mystical.cloud.auth.service.UserService;
import com.mystical.cloud.auth.utils.JwtTokenUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @author: MysticalYcc
 * @Date: 2020/12/7
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "user")
public class UserInfoController {

    @Autowired
    UserService userService;
    @PostMapping("/info")
    public Object userInfo(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String authToken = authHeader.substring("Bearer ".length());
            String userName = JwtTokenUtil.parseToken(authToken);
            QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username",userName);
            UserInfo one = userService.getOne(queryWrapper);
            if(one!=null){
                return one;
            }else {
               throw new EventBaseException("未知账户");
            }
        }else {
            throw new EventBaseException("错误的token");
        }
    }
}
