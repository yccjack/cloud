package com.mystical.cloud.entrance;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mystical.cloud.entrance.mapper")
public class EntranceApplication {


    public static void main(String[] args) {
        SpringApplication.run(EntranceApplication.class, args);
    }

}
