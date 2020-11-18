package com.mystical.cloud.auth.signature.entity;


import com.mystical.cloud.auth.signature.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@SignedEntity
@ToString
@Setter
@Getter
public class SignedParam {
    @SignedAppId
    private String appId;
    @SignedIgnore
    private String data;
    @SignedTimestamp
    private long timestamp;
    @SignedNonce
    private int nonce;
    @Signature
    private String signature;

}

