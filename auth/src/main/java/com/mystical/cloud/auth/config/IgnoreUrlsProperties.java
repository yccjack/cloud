package com.mystical.cloud.auth.config;

/**
 * @Description:
 * @author: MysticalYcc
 * @Date: 2021/9/23
 */

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于配置不需要保护的资源路径
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsProperties {
    /**
     * 接口资源
     */
    private List<String> urls = new ArrayList<>();

    /**
     * 静态资源
     */
    private List<String> resources = new ArrayList<>();
}