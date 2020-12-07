package com.mystical.cloud.auth.controller;

import com.mystical.cloud.auth.response.CommonResponse;
import com.mystical.cloud.auth.response.CommonResultEnum;
import com.mystical.cloud.auth.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
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
public class UserInfo {

    @PostMapping("/info")
    public CommonResponse<Object> userInfo(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String authToken = authHeader.substring("Bearer ".length());
            String userName = JwtTokenUtil.parseToken(authToken);

            return new CommonResponse<>(CommonResultEnum.SUCCESS);
        }else {
            return new CommonResponse<>(CommonResultEnum.REGISTER_TOKEN_MISS);
        }
    }
}
