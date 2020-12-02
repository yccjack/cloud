package com.mystical.cloud.entrance.message.bean;

import lombok.*;

/**
 * @author MysticalYcc
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto {
    private int id;
    private String eventId;
    private String nodeId;
    private String nodeOperation;
    private boolean eventStatus;
    private String eventInfoId;
}
