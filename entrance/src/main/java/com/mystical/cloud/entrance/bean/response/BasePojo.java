package com.mystical.cloud.entrance.bean.response;

import com.alibaba.fastjson.JSON;

public abstract class BasePojo implements IPojo {
    public BasePojo() {
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }
}
