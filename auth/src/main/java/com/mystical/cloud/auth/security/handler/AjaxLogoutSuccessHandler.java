package com.mystical.cloud.auth.security.handler;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mystical.cloud.auth.bean.AjaxResponseBody;
import com.mystical.cloud.auth.bean.LoginView;
import com.mystical.cloud.auth.service.LoginService;
import com.mystical.cloud.auth.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * // 注销成功返回的 JSON 格式数据给前端（否则为 登录时的 html）
 */
@Component
public class AjaxLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    LoginService loginService;
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        AjaxResponseBody responseBody = new AjaxResponseBody();
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) {
            String authHeader = httpServletRequest.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                final String authToken = authHeader.substring("Bearer ".length());

                String username = JwtTokenUtil.parseToken(authToken);
                UpdateWrapper<LoginView> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("username", username).set("status", 0);
                loginService.update(updateWrapper);
            }
        }
        responseBody.setStatus("100");
        responseBody.setMsg("注销成功");
        httpServletResponse.setCharacterEncoding("utf-8");
//        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//        httpServletResponse.setHeader("Cache-Control","no-cache");
        httpServletResponse.getWriter().write(JSON.toJSONString(responseBody));
    }
}
