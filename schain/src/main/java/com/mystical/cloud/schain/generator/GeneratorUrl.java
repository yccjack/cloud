package com.mystical.cloud.schain.generator;


import lombok.Data;

import java.util.Date;

@Data
public class GeneratorUrl {
    private static final String shortPre = "http://localhost:8800/atpapi/cuuser/auth/short/redirect/";
    private Long id;
    private String lUrl;
    private String sUrl;
    private Object generatorId;
    private Date expireTime;
    private Date createTime;


    public void setGeneratorId(Object generatorId) {
        this.generatorId = generatorId;
        this.sUrl = shortPre + generatorId;
    }
}
