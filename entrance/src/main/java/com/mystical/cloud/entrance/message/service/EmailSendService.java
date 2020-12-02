package com.mystical.cloud.entrance.message.service;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

import com.mystical.cloud.entrance.message.bean.BaseDto;
import com.mystical.cloud.entrance.message.utils.FutureCallBackTask;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.concurrent.Callable;

/**
 * @author MysticalYcc
 * 异步组装eamil并发送，发送成功或者失败回调 futureCallBackTask 进行处理。
 * 1.保存信息到数据库，留存补偿；
 * 2.异步组装，发送并保存信息，
 * 3.回调suceedd方法更新event信息，打印日志；
 */
@Repository("emailSendService")
@Log4j2
public class EmailSendService extends AbstractService<String> {
    private ListeningExecutorService listeningExecutorService;
    private FutureCallBackTask futureCallBackTask;
    private DisposeService emailDisposeService;

    @Override
    protected boolean deal(String param) {
        boolean success;
        try {
            BaseDto baseDto = JSON.parseObject(param, BaseDto.class);
            success = sendEmail(baseDto);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return success;
    }


    public boolean sendEmail(BaseDto baseDto) {
        int save = 1;//保存数据
        if (save > 0) {
            ListenableFuture<Integer> listenableFuture = listeningExecutorService.submit(new EmailRunnable(baseDto, emailDisposeService));
            Futures.addCallback(listenableFuture, futureCallBackTask, listeningExecutorService);
        }

        return true;
    }

    static class EmailRunnable implements Callable<Integer> {
        BaseDto baseDto;
        private DisposeService emailDisposeService;

        public EmailRunnable(BaseDto baseDto, DisposeService emailDisposeService) {
            this.baseDto = baseDto;
            this.emailDisposeService = emailDisposeService;
        }

        @Override
        public Integer call() throws Exception {
            //todo 组装email消息发送
            emailDisposeService.deal(baseDto);
            log.info("组装email成功，准备发送email。");
            return baseDto.getId();
        }

    }

    @Autowired
    public void setListeningExecutorService(ListeningExecutorService listeningExecutorService) {
        this.listeningExecutorService = listeningExecutorService;
    }


    @Autowired
    public void setFutureCallBackTask(FutureCallBackTask futureCallBackTask) {
        this.futureCallBackTask = futureCallBackTask;
    }

    @Autowired
    public void setEmailDisposeService(DisposeService emailDisposeService) {
        this.emailDisposeService = emailDisposeService;
    }
}


