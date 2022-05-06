package com.ling.staservice.client;

import com.ling.commonutils.R;
import com.ling.staservice.client.Impl.UcenterClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {
    //根据日期，获取那天注册人数
    @GetMapping("/serviceUcenter/ucenter-member/registerCount/{day}")
    public R countRegister(@PathVariable("day") String day);
}
