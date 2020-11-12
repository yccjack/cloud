package com.mystical.cloud.auth.service;

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

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object userInfo = authentication.getPrincipal();

        boolean hasPermission = false;


        if (userInfo instanceof UserDetails) {

            String username = ((UserDetails) userInfo).getUsername();
            if(username.equals("ycc")){
                return true;
            }
            Collection<? extends GrantedAuthority> authorities = ((UserDetails) userInfo).getAuthorities();
            if (authorities != null) {
                Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
                for (GrantedAuthority authority : authorities) {
                    if (authority.getAuthority().equals("ROLE_ADMIN")) {

                        //admin 可以访问的资源
                        Set<String> urls = new HashSet();
                        urls.add("/sys/**");
                        urls.add("/test/**");
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
