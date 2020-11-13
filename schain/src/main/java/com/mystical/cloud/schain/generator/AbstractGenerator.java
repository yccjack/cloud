package com.mystical.cloud.schain.generator;



public abstract class AbstractGenerator implements Generator {

    /**
     * @param url
     * @return
     */
    public abstract GeneratorUrl shorten(String url);

    /**
     * 通过短链获取真实URL
     *
     * @param surl
     * @return
     */
    public abstract GeneratorUrl getUrl(String surl);


}
