package com.mystical.cloud.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mystical.cloud.auth.bean.LoginView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 权限认证在这里处理
 */
@Component("rbacauthorityservice")
public class RbacAuthorityService {
    @Autowired
    LoginService loginService;

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {


        Object userInfo = authentication.getPrincipal();

        boolean hasPermission = false;


        if (userInfo instanceof UserDetails) {

            String username = ((UserDetails) userInfo).getUsername();
            if (username.equals("admin")) {
                QueryWrapper<LoginView> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("username", "admin").eq("status", 1);
                LoginView admin = loginService.getOne(queryWrapper);
                if (admin != null) {
                    return true;
                } else {
                    throw new RuntimeException("admin未登录，请登录正确账户");
                }
            }
            Collection<? extends GrantedAuthority> authorities = ((UserDetails) userInfo).getAuthorities();
            if (authorities != null) {
                Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
                for (GrantedAuthority authority : authorities) {
                    if (authority.getAuthority().equals("ROLE_ADMIN")) {

                        //admin 可以访问的资源
                        Set<String> urls = new HashSet();
                        urls.add("/**/**");
                        AntPathMatcher antPathMatcher = new AntPathMatcher();
                        for (String url : urls) {
                            if (antPathMatcher.match(url, request.getRequestURI())) {
                                hasPermission = true;
                                break;
                            }
                        }
                    }
                }
            }

            //user可以访问的资源
            Set<String> urls = new HashSet();
            urls.add("/user/**");
            urls.add("/index/**");
            AntPathMatcher antPathMatcher = new AntPathMatcher();
            for (String url : urls) {
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }
            return hasPermission;
        } else {
            return false;
        }


    }
}
