package com.mystical.cloud.gateway.service;

import com.mystical.cloud.gateway.response.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "auth-service")
public interface AuthService {

    @RequestMapping(value = "/auth/info", method = RequestMethod.POST)
    CommonResponse<String> getAuthInfo(@RequestBody String request);
}
