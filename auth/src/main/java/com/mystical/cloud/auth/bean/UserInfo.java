package com.mystical.cloud.auth.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description:
 * @author: MysticalYcc
 * @Date: 2020/12/4
 */
@Data
@TableName("user_info")
public class UserInfo {
    private long id;
    private String username;
    private String password;
    private String headImgUrl;

}
