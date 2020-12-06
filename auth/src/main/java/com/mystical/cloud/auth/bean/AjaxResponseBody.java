package com.mystical.cloud.auth.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class AjaxResponseBody implements Serializable {
    private String status;
    private String msg;
    private Object result;
    private String jwtToken;
    private String index;
}