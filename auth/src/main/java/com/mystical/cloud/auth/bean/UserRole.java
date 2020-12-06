package com.mystical.cloud.auth.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ycc
 * @time 15:44
 */
@Data
@TableName("user_role")
public class UserRole {
    private long id;
    private String username;
    private String rolename;
}
