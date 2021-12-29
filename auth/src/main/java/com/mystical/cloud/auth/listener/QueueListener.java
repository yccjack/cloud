package com.mystical.cloud.auth.listener;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mystical.cloud.auth.bean.UserInfo;
import com.mystical.cloud.auth.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ArrayBlockingQueue;

@Repository
@Log4j2
public class QueueListener implements InitializingBean {

    public static ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1024);

    @Autowired
    UserMapper userMapper;

    @Override
    public void afterPropertiesSet() {
        Thread t1 =new Thread(()->{
            while (true){
                try {
                    String take = queue.take();
                    if(StringUtils.isNotBlank(take)){
                        UserInfo userInfo = userMapper.selectById(1);
                        String username = userInfo.getUsername();
                        userInfo.setUsername("queue-name");
                        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
                        log.info(userInfo);
                        updateWrapper.eq("username",username);
                        int update = userMapper.update(userInfo, updateWrapper);
                        log.info("更新数据库:username:[{}]",username);
                        if(update==0){
                            log.info("更新失败，未读取到事务提交后的值");
                        }else {
                            log.info("更新成功！");
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    return;
                }
            }
        });
        t1.start();
    }
}
