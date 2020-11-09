package com.mystical.cloud.auth.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class SelfUserDetailsService implements UserDetailsService {
    public UserDetails loadUserByUsername(String username) {
        SelfUserDetails userDetails  =new SelfUserDetails();
        userDetails.setUsername("ycc");
        String pwd = BCrypt.hashpw("123456", BCrypt.gensalt());
        userDetails.setPassword(pwd);
        return userDetails;
    }
}
