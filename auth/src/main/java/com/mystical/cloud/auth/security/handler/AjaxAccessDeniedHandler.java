package com.mystical.cloud.auth.security.handler;

import com.alibaba.fastjson.JSON;
import com.mystical.cloud.auth.bean.AjaxResponseBody;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  // 无权访问返回的 JSON 格式数据给前端（否则为 403 html 页面）
 */
@Component
public class AjaxAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        AjaxResponseBody responseBody = new AjaxResponseBody();

        responseBody.setStatus("300");
        responseBody.setMsg("需要权限!");
        httpServletResponse.setCharacterEncoding("utf-8");
//        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//        httpServletResponse.setHeader("Cache-Control","no-cache");
        httpServletResponse.getWriter().write(JSON.toJSONString(responseBody));
    }
}







