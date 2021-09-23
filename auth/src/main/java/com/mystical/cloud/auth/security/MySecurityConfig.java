package com.mystical.cloud.auth.security;

import com.mystical.cloud.auth.config.IgnoreUrlsProperties;
import com.mystical.cloud.auth.security.handler.*;
import com.mystical.cloud.auth.service.SelfUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AjaxAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    AjaxAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    AjaxAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    AjaxLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    AjaxAccessDeniedHandler accessDeniedHandler;

    @Autowired
    SelfUserDetailsService userDetailsService;

    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    IgnoreUrlsProperties ignoreUrlsProperties;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 加入自定义的安全认证
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    // 对于获取token的rest api要允许匿名访问
    @Override
    public void configure(WebSecurity web) throws Exception {
        ignoreUrlsProperties.getResources().forEach(resource -> web.ignoring().antMatchers(resource));
        web.ignoring(). antMatchers("/swagger-ui.html")
                .antMatchers("/webjars/**")
                .antMatchers("/v2/**")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/auth/*")
                .antMatchers("/signature/*")
                .antMatchers("/**/mq/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 去掉 CSRF（跨域）
        http.csrf().disable()
                // 使用 JWT，关闭session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                //  未登陆时返回 JSON
                .httpBasic().authenticationEntryPoint(authenticationEntryPoint)
                .and()

                // 所有请求必须认证
                .authorizeRequests()

                // 允许对于网站静态资源的无授权访问
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.jpg",
                        "/**/*.png"
                ).permitAll()
                .anyRequest()
                // 认证的逻辑
                // RBAC 动态 url 认证
                .access("@rbacauthorityservice.hasPermission(request,authentication)")
                .and()
                //开启登录
                .formLogin()
                .loginPage("/login")
                // 登录成功
                .successHandler(authenticationSuccessHandler)
                // 登录失败
                .failureHandler(authenticationFailureHandler)
                .permitAll()
                .and()
                // 登出
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll();

        // 记住我
        http.rememberMe().rememberMeParameter("remember-me")
                .userDetailsService(userDetailsService).tokenValiditySeconds(300);
// 无权访问 JSON 格式的数据
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        //用重写的Filter替换掉原有的UsernamePasswordAuthenticationFilter实现使用json 数据也可以登陆
        http.addFilterAt(customAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class);
// 设置执行其他工作前的 filter （最重要的验证 JWT）
        // JWT Filter
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * //注册自定义的UsernamePasswordAuthenticationFilter
     *
     * @return
     * @throws Exception
     */
    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        // 设置登陆接口名
        filter.setFilterProcessesUrl("/login");

        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
}