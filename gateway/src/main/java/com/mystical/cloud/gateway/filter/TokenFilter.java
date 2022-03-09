package com.mystical.cloud.gateway.filter;

import com.mystical.cloud.gateway.service.AuthService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author MysticalYcc
 * 过滤token
 */
@Log
@Component
public class TokenFilter implements GlobalFilter, Ordered {

    @Autowired
    AuthService authService;

    @Value("${localhost.test}")
    private boolean test;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if(test){
            return chain.filter(exchange);
        }
        String url1 = exchange.getRequest().getURI().toString();
        //放行特定的请求地址
        if (url1.indexOf("/uploadFile") > 0 || url1.indexOf("/upload") > 0 || url1.indexOf("/download") > 0 || url1.indexOf("/login") > 0 || url1.indexOf("/auth") > 0) {
            return chain.filter(exchange);
        }
        MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();
        List<HttpCookie> userInfo = cookies.get("userInfo");
        HttpCookie httpCookie=null;
        if(userInfo!=null&&userInfo.size()>0){
            httpCookie  = userInfo.get(0);
        }

        String cookieToken = httpCookie==null?null:httpCookie.getValue();

        if(cookieToken==null){
            String token = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (token == null || token.isEmpty()) {
                log.info("token is empty...");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            } else {
                if (validateToken(exchange, token)) {
                    return exchange.getResponse().setComplete();
                }
            }
        }else {
            if (validateToken(exchange, cookieToken)) {
                return exchange.getResponse().setComplete();
            }
        }

        return chain.filter(exchange);
    }

    private boolean validateToken(ServerWebExchange exchange, String cookieToken) {
        log.info("authenticate token start...");
        if (cookieToken.contains("Bearer")) {
            cookieToken = cookieToken.substring(cookieToken.indexOf("Bearer ") + 7);
        }
        boolean authInfo = authService.getAuthInfo(cookieToken);
        if (authInfo) {
            return false;
        }
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return true;
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
