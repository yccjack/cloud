package com.mystical.cloud.gateway.limit;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 地址限流
 */
@Repository
public class HostAddrKeyResolver implements KeyResolver
{

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress());
    }

}
