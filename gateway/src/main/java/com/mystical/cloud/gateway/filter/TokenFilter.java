package com.mystical.cloud.gateway.filter;

import com.mystical.cloud.gateway.response.CommonResponse;
import com.mystical.cloud.gateway.service.AuthService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author MysticalYcc
 * 过滤token
 */
@Log
@Repository
public class TokenFilter implements GlobalFilter, Ordered {

    @Autowired(required = false)
    AuthService authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url1 = exchange.getRequest().getURI().toString();
        if (url1.indexOf("/uploadFile") > 0 || url1.indexOf("/upload") > 0 || url1.indexOf("/download") > 0 || url1.indexOf("/login") > 0 || url1.indexOf("/auth") > 0) {
            return chain.filter(exchange);
        }
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (token == null || token.isEmpty()) {
            log.info("token is empty...");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        } else {
            log.info("authenticate token start...");
            CommonResponse<String> authInfo = authService.getAuthInfo(token);
            if (!"200".equals(authInfo.getCode())) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
