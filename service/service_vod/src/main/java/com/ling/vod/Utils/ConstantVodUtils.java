package com.ling.vod.Utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantVodUtils implements InitializingBean {
    @Value("${aliyun.vod.file.keyid}")
    private String keyid;

    @Value("${aliyun.vod.file.keysecret}")
    private String keysecret;


    public static String ACCESSKEY_ID;
    public static String ACCESSKEY_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESSKEY_ID = this.keyid;
        ACCESSKEY_SECRET = this.keysecret;

    }
}
