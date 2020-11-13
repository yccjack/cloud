package com.mystical.cloud.schain.generator;

public interface ShortIdFilter {
    boolean filter(Object generatorId);

    String getLongUrl(String shortId);

    GeneratorUrl getShortUrl(String surl);
}
