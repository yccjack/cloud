package com.mystical.cloud.auth.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mystical.cloud.auth.bean.LoginView;
import com.mystical.cloud.auth.bean.UserInfo;
import com.mystical.cloud.auth.mapper.LoginMapper;
import com.mystical.cloud.auth.mapper.UserMapper;
import com.mystical.cloud.auth.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author: MysticalYcc
 * @Date: 2020/12/4
 */
@Service
public class LoginService extends ServiceImpl<LoginMapper, LoginView> {

    @Autowired
    UserMapper userMapper;

    public boolean saveAndValidate(UserInfo userInfo) {
        boolean succeed = false;
        int insert = userMapper.insert(userInfo);
        if (insert > 0) {
            succeed = true;
        }
        return succeed;
    }

    public String getRegisterToken(String username,int status) {
        String jwtToken = JwtTokenUtil.generateToken(username, 30000);
        LoginView loginView = new LoginView();
        loginView.setUsername(username);
        loginView.setStatus(status);
        loginView.setToken(jwtToken);
        this.saveOrUpdate(loginView);
        return jwtToken;
    }
}
