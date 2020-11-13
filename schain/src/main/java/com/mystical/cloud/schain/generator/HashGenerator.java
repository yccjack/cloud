package com.mystical.cloud.schain.generator;

import cn.hutool.core.lang.MurmurHash;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Calendar;
import java.util.UUID;

/**
 * @author ycc
 */
@Slf4j
@Component
public class HashGenerator extends AbstractGenerator {


    @Autowired
    ShortIdFilter shortIdFilter;

    @Override
    public GeneratorUrl shorten(String url) {
        GeneratorUrl urlObject = new GeneratorUrl();
        try {
            String encode = CharUtil.encode(url);
            urlObject.setLUrl(encode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return doShorten(urlObject);
    }

    private GeneratorUrl doShorten(GeneratorUrl urlObject) {
        getShortId(urlObject);
        //查询是否存在同样的shortId
        boolean has = shortIdFilter.filter(urlObject.getGeneratorId());
        if (has) {
            return urlObject;
        } else {
            //todo 保存
        }
        return urlObject;
    }

    private void getShortId(GeneratorUrl urlObject) {
        String lUrl = urlObject.getLUrl();
        long id = MurmurHash.hash64(lUrl);
        //处理传入的地址可能存在中文转码的问题
        String shortId = ConversionUtil.encode(id);
        urlObject.setGeneratorId(shortId);
        boolean has = shortIdFilter.filter(shortId);
        if (has) {
            // 短链冲突，检查是否是同一个长链。
            if (!lUrl.equals(shortIdFilter.getLongUrl(shortId))) {
                String uuid = UUID.randomUUID().toString();
                String conflictShortUrl = lUrl + uuid;
                urlObject.setLUrl(conflictShortUrl);
                getShortId(urlObject);
            }
        }
    }

    @Override
    public GeneratorUrl getUrl(String surl) {
        return shortIdFilter.getShortUrl(surl);

    }


    public static long getMi(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + day);
        return calendar.getTimeInMillis();

    }

}


