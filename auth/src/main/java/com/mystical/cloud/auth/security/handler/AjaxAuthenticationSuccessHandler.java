package com.mystical.cloud.auth.security.handler;

import com.alibaba.fastjson.JSON;
import com.mystical.cloud.auth.bean.AjaxResponseBody;
import com.mystical.cloud.auth.bean.SelfUserDetails;
import com.mystical.cloud.auth.utils.JwtTokenUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 登陆成功
@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AjaxResponseBody responseBody = new AjaxResponseBody();

        responseBody.setStatus("00");
        responseBody.setMsg("登陆成功!");

        SelfUserDetails selfUserDetails = (SelfUserDetails) authentication.getPrincipal();

// 创建 token ，并返回 ，设置过期时间为 300 秒
        String jwtToken = JwtTokenUtil.generateToken(selfUserDetails.getUsername(), 300);
        responseBody.setJwtToken(jwtToken);
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(JSON.toJSONString(responseBody));
    }
}