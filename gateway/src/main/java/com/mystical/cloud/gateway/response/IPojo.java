package com.mystical.cloud.gateway.response;

import java.io.Serializable;

public interface IPojo extends Serializable {
    String toJsonString();
}
