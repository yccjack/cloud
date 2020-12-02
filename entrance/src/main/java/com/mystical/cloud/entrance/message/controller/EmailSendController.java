package com.mystical.cloud.entrance.message.controller;

import com.mystical.cloud.entrance.bean.response.CommonResponse;
import com.mystical.cloud.entrance.message.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MysticalYcc
 */
@RestController
@RequestMapping("msg")
public class EmailSendController {


    private BaseService<String> emailSendService;

    @RequestMapping(
            value = "/send",
            method = RequestMethod.POST
    )
    public CommonResponse<Boolean> sendEmail(@RequestBody String json) {

        return emailSendService.dispatch(json);
    }

    @Autowired
    public EmailSendController(BaseService<String> emailSendService) {
        this.emailSendService = emailSendService;
    }





    public EmailSendController(){

    }


   static ThreadLocal<Integer> numThreadLocal = ThreadLocal.withInitial(() -> 0);
    public static void main(String[] args) {
        while (true){
            run();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void run(){
        start();
    }

    private static void start() {
        Integer integer = numThreadLocal.get();
        numThreadLocal.set(++integer);
        System.out.println(++integer);
    }
}
