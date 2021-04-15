package com.mystical.cloud.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mystical.cloud.auth.bean.LoginView;
import com.mystical.cloud.auth.bean.UserInfo;
import com.mystical.cloud.auth.mapper.LoginMapper;
import com.mystical.cloud.auth.mapper.UserMapper;
import com.mystical.cloud.auth.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @author: MysticalYcc
 * @Date: 2020/12/4
 */
@Service
public class LoginService extends ServiceImpl<LoginMapper, LoginView> {

    @Autowired
    UserMapper userMapper;

    @Value("${cookie.domain}")
    private String cookiesDomain;

    public boolean saveAndValidate(UserInfo userInfo) {
        boolean succeed = false;
        int insert = userMapper.insert(userInfo);
        if (insert > 0) {
            succeed = true;
        }
        return succeed;
    }

    public String getRegisterToken(String username, int status,HttpServletResponse response) {
        String jwtToken = JwtTokenUtil.generateToken(username, 30000);
        LoginView loginView = new LoginView();
        loginView.setUsername(username);
        loginView.setStatus(status);
        loginView.setToken(jwtToken);
        this.saveOrUpdate(loginView);
        //写入cookie
        setCookie(response, jwtToken);
        return jwtToken;
    }

    public boolean login(UserInfo userInfo, HttpServletResponse response) {

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userInfo.getUsername()).eq("password", userInfo.getPassword());
        Integer integer = userMapper.selectCount(queryWrapper);
        if (integer == 1) {
            String jwtToken = JwtTokenUtil.generateToken(userInfo.getUsername(), 30000);
            setCookie(response, jwtToken);
            return true;
        }
        return false;
    }

    /**
     *  写入cookie
     * @param response {@link HttpServletResponse}
     * @param jwtToken  {@link JwtTokenUtil generateToken()}
     */
    private void setCookie(HttpServletResponse response, String jwtToken) {

        Cookie cookie = new Cookie("userInfo", jwtToken);
        cookie.setDomain(cookiesDomain);
        cookie.setPath("/");
        //失效时间 一个月
        cookie.setMaxAge(2592000);
        response.addCookie(cookie);
    }
}
