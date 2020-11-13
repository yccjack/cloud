package com.mystical.cloud.schain.service;

import com.mystical.cloud.schain.generator.GeneratorUrl;
import com.mystical.cloud.schain.generator.ShortIdFilter;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlService implements ShortIdFilter {
    @Override
    public boolean filter(Object generatorId) {
        //todo 实现对生成的shortId过滤
        return false;
    }

    @Override
    public String getLongUrl(String shortId) {
        //todo 通过shortId 获取longUrl
        return null;
    }

    @Override
    public GeneratorUrl getShortUrl(String surl) {
        //todo 通过shortId获取GeneratorUrl
        return null;
    }
}
