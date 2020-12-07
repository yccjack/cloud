package com.mystical.cloud.auth.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mystical.cloud.auth.bean.UserInfo;
import com.mystical.cloud.auth.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author: MysticalYcc
 * @Date: 2020/12/7
 */
@Service
public class UserService extends ServiceImpl<UserMapper, UserInfo> {
}
