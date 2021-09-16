package com.mystical.cloud.auth.security.handler;

import com.alibaba.fastjson.JSON;
import com.mystical.cloud.auth.bean.AjaxResponseBody;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import org.springframework.security.core.AuthenticationException ;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未登陆时返回 JSON 格式的数据给前端（否则为 html）
 */
@Component
public class AjaxAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        AjaxResponseBody responseBody = new AjaxResponseBody();

        responseBody.setStatus("000");
        responseBody.setMsg("未登录!");
        httpServletResponse.setCharacterEncoding("utf-8");
//        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//        httpServletResponse.setHeader("Cache-Control","no-cache");
        httpServletResponse.getWriter().write(JSON.toJSONString(responseBody));
    }
}