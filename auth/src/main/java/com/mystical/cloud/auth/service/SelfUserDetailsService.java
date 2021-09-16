package com.mystical.cloud.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mystical.cloud.auth.bean.SelfUserDetails;
import com.mystical.cloud.auth.bean.UserInfo;
import com.mystical.cloud.auth.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    UserMapper userMapper;
    public static Map<String, String> loginMap = new ConcurrentHashMap<>(1024);

    @Override
    public UserDetails loadUserByUsername(String username) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        UserInfo userInfo = userMapper.selectOne(queryWrapper);
        SelfUserDetails userDetails = new SelfUserDetails();
        userDetails.setUsername(userInfo.getUsername());
        String pwd = BCrypt.hashpw(userInfo.getPassword(), BCrypt.gensalt());
        userDetails.setPassword(pwd);
        return userDetails;
    }
}
