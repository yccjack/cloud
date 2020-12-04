package com.mystical.cloud.auth.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mystical.cloud.auth.bean.LoginView;
import com.mystical.cloud.auth.mapper.LoginMapper;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author: MysticalYcc
 * @Date: 2020/12/4
 */
@Service
public class LoginService  extends ServiceImpl<LoginMapper, LoginView> {
}
