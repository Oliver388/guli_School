package com.ling.orderservice.client;

import com.ling.commonutils.vo.EduCourseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author linglifan
 * @date 2022/08/05 13:57
 */
@Component
@FeignClient("service-edu")
public interface EduClient {

    //根据课程id查询课程信息
    @GetMapping("/eduservice/course/getDto/{courseId}")
    public EduCourseVo getCourseInfoDto(@PathVariable String courseId);

}
