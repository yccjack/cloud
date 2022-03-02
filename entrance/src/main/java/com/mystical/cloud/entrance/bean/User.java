package com.mystical.cloud.entrance.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("test_user")
@Data
public class User {

    private int id;
    @TableField("user_id")
    private String userId;
    @TableField("user_name")
    private String userName;
    private String phone;
    @TableField("lan_id")
    private int lanId;
    @TableField("region_id")
    private int regionId;
    @TableField("create_time")
    private Date createTime;

}
