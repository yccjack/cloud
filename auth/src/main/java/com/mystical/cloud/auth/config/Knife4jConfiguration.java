package com.mystical.cloud.auth.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @Description:
 * @author: MysticalYcc
 * @Date: 2021/9/23
 */

@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("Knife Docs")
                        .description("# Knife Docs")
                        .termsOfServiceUrl("https://gschaos.club")
                        .contact("wangxb147@chinaunicom.cn")
                        .version("1.0.1")
                        .build())
                //分组名称
                .groupName("2.X版本")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Knife Docs")
                .description("Knife Docs")
                .version("1.0.0")
                .contact(new Contact("MysticalYcc", "", "568166723@qq.com"))
                .build();
    }

    private List<ApiKey> security() {
        return newArrayList(
                new ApiKey("Authorization", "Authorization", "header")
        );
    }
}
