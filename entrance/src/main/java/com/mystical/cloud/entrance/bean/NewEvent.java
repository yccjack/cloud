package com.mystical.cloud.entrance.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * @author MysticalYcc
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_base_event")
public class NewEvent {
        private int id;
        private String eventId;
        private String nodeId;
        private String nodeOperation;
        private boolean eventStatus;
        private String eventInfoId;
}
