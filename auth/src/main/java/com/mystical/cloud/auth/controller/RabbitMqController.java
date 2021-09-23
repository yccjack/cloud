package com.mystical.cloud.auth.controller;
import com.mystical.cloud.auth.mq.RabbitProducer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @Description:
 * @author: MysticalYcc
 * @Date: 2021/9/23
 */



@RestController
@Api(tags = "mq")
@RequestMapping("mq")
public class RabbitMqController {

    @Autowired
    private RabbitProducer rabbitProducer;

    @GetMapping("/sendDemoQueue")
    @ApiOperation(value = "sendDemoQueue", notes = "sendDemoQueue")
    public Object sendDemoQueue() {
        rabbitProducer.sendDemoQueue();
        return "success";
    }
}