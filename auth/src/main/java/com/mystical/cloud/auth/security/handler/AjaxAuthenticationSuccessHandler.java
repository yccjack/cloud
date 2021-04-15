package com.mystical.cloud.auth.security.handler;

import com.alibaba.fastjson.JSON;
import com.mystical.cloud.auth.bean.AjaxResponseBody;
import com.mystical.cloud.auth.bean.LoginView;
import com.mystical.cloud.auth.bean.SelfUserDetails;
import com.mystical.cloud.auth.mapper.LoginMapper;
import com.mystical.cloud.auth.mapper.UserMapper;
import com.mystical.cloud.auth.service.LoginService;
import com.mystical.cloud.auth.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * // 登录成功返回的 JSON 格式数据给前端（否则为 html）
 */
@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    LoginService loginService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AjaxResponseBody responseBody = new AjaxResponseBody();

        responseBody.setStatus("00");
        responseBody.setMsg("登陆成功!");

        SelfUserDetails selfUserDetails = (SelfUserDetails) authentication.getPrincipal();

// 创建 token ，并返回 ，设置过期时间为 300000 秒
        String jwtToken = loginService.getRegisterToken(selfUserDetails.getUsername(),1,response);
        responseBody.setJwtToken(jwtToken);
        responseBody.setIndex("index.html");
        response.setCharacterEncoding("utf-8");
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Cache-Control","no-cache");
        response.getWriter().write(JSON.toJSONString(responseBody));
    }
}