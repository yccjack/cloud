package com.mystical.cloud.auth.security;

import com.mystical.cloud.auth.bean.SelfUserDetails;
import com.mystical.cloud.auth.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义 json 登录
 *
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //attempt Authentication when Content-Type is json
        if(request.getContentType()==null){
            return null;
        }
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)
                || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {



            UsernamePasswordAuthenticationToken authRequest = null;
            try {
                String jsonString = GetRequestJsonUtils.getPostRequestJsonString(request);
                if(StringUtils.isEmpty(jsonString)){
                    authRequest = new UsernamePasswordAuthenticationToken(
                            "", "");
                }else {
                    SelfUserDetails selfUserDetails = JsonUtils.jsonToPojo(jsonString,SelfUserDetails.class);
                    assert selfUserDetails != null;
                    authRequest = new UsernamePasswordAuthenticationToken(
                            selfUserDetails.getUsername(), selfUserDetails.getPassword());
                }
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            } catch (Exception e) {
                e.printStackTrace();
                authRequest = new UsernamePasswordAuthenticationToken(
                        "", "");
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        }
        //transmit it to UsernamePasswordAuthenticationFilter
        else {
            return super.attemptAuthentication(request, response);
        }
    }
}
