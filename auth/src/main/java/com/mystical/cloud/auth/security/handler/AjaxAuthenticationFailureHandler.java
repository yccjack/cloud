package com.mystical.cloud.auth.security.handler;

import com.alibaba.fastjson.JSON;
import com.mystical.cloud.auth.bean.AjaxResponseBody;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * //  登录失败返回的 JSON 格式数据给前端（否则为 html）
 */
@Component
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        AjaxResponseBody responseBody = new AjaxResponseBody();

        responseBody.setStatus("400");
        responseBody.setMsg("登陆失败");
        httpServletResponse.setCharacterEncoding("utf-8");
//        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//        httpServletResponse.setHeader("Cache-Control","no-cache");
        httpServletResponse.getWriter().write(JSON.toJSONString(responseBody));
    }

}