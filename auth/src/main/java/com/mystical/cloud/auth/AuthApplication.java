package com.mystical.cloud.auth;


import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mystical.cloud.auth.mapper")
@Log4j2
public class AuthApplication {

    public static void main(String[] args) {
       log.info("程序启动");
        SpringApplication.run(AuthApplication.class, args);
        log.info("程序关闭");
    }

}
