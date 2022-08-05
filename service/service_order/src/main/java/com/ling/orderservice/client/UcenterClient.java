package com.ling.orderservice.client;

import com.ling.commonutils.vo.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author linglifan
 * @date 2022/08/05 14:01
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    //根据课程id查询课程信息
    @PostMapping("/educenter/member/getInfoUc/{id}")
    public UcenterMemberVo getInfo(@PathVariable String id);
}
