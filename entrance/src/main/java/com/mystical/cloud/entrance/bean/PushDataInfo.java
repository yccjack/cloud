package com.mystical.cloud.entrance.bean;

import lombok.Data;

/**
 * @Description:
 * @author: MysticalYcc
 * @Date: 2020/12/28
 */
@Data
public class PushDataInfo extends PushDto {


    /**
     * 这条数据决定需不需要发送
     */
    private boolean needSend = true;


    private int type;

    /**
     * 推送类型
     */
    private String strategy = "location";

}
