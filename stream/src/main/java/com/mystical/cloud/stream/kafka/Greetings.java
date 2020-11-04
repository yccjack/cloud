package com.mystical.cloud.stream.kafka;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class Greetings {
    private long timestamp;
    private String message;

    public Greetings(long timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public Greetings() {

    }
}
