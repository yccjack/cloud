package com.mystical.cloud.gateway.service;

import com.mystical.cloud.gateway.response.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service")
public interface AuthService {

    @RequestMapping(value = "/auth/info", method = RequestMethod.GET)
    CommonResponse<String> getAuthInfo(@RequestParam("token") String token);
}
