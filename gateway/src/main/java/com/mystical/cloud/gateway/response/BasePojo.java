package com.mystical.cloud.gateway.response;

import com.alibaba.fastjson.JSON;

public abstract class BasePojo implements IPojo {
    public BasePojo() {
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }
}
