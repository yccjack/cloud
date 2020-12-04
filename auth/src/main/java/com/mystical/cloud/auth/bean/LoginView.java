package com.mystical.cloud.auth.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description:
 * @author: MysticalYcc
 * @Date: 2020/12/4
 */
@Data
@TableName("login_view")
public class LoginView {
    private long id;
    private String username;
    private int status;
    private String token;

}
