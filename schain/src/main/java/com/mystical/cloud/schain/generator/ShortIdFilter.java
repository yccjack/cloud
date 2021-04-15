package com.mystical.cloud.schain.generator;

/**
 * @author MysticalYcc
 */
public interface ShortIdFilter {
    /**
     * 判断短链是否重复
     * @param generatorId 生成的短链ID
     * @return 是否含有
     */
    boolean filter(Object generatorId);

    /**
     * 获取长链
     * @param shortId 短链id
     * @return 长链
     */
    String getLongUrl(String shortId);

    /**
     * 获取短链
     * @param surl 短链
     * @return
     */
    GeneratorUrl getShortUrl(String surl);
}
