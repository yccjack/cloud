package com.mystical.cloud.entrance.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author MysticalYcc
 */
@ToString
@Setter
@Getter
public class BaseDto {
    private String source;

    private String operation;

    private String event;

    private String data;
}
