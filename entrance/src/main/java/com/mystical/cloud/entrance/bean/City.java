package com.mystical.cloud.entrance.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("province")
@Data
public class City {
    private long id;
    private long code;
    private String name;
    private String province;
    private String city;
    private String area;
    private String town;
}
