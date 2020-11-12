package com.mystical.cloud.auth.service;

import com.mystical.cloud.auth.bean.SelfUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 根据username获取用户信息
 */
@Service
public class SelfUserDetailsService implements UserDetailsService {

    public static Map<String,String> loginMap = new ConcurrentHashMap<>(1024);
    public UserDetails loadUserByUsername(String username) {

        SelfUserDetails userDetails  =new SelfUserDetails();
        userDetails.setUsername("ycc");
        String pwd = BCrypt.hashpw("123456", BCrypt.gensalt());
        userDetails.setPassword(pwd);
        return userDetails;
    }
}
