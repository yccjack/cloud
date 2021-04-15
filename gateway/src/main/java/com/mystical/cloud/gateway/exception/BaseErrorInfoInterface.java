package com.mystical.cloud.gateway.exception;

/**
 * @Description:
 * @author: MysticalYcc
 * @Date: 2021/4/15
 */
public interface BaseErrorInfoInterface {
    /** 错误码*/
    String getResultCode();

    /** 错误描述*/
    String getResultMsg();
}
