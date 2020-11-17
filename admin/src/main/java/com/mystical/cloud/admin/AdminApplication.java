package com.mystical.cloud.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class AdminApplication {

    public static void main(String[] args) {
        testThread();
        SpringApplication.run(AdminApplication.class, args);
    }


    private static volatile  boolean flag = true;
    public static void testThread(){
        Thread thread = new Thread(() -> {
            while (flag){
                try {
                    Thread.sleep(10000);
                    System.out.println("业务处理中...........");
                    while (flag){
                        System.out.println("模拟卡住");
                        Thread.sleep(100000);
                    }
                } catch (InterruptedException e) {
                    System.out.println("响应中断退出");
                    e.printStackTrace();
                    throw new RuntimeException("异常退出");
                }
                System.out.println("标志位退出，正常退出");
            }
        });
        thread.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = false;
        System.out.println("设置flag未停止！");
        thread.interrupt();
    }

}
