package com.mystical.cloud.auth.response;

import com.alibaba.fastjson.JSON;

public abstract class BasePojo implements IPojo {
    public BasePojo() {
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }
}
