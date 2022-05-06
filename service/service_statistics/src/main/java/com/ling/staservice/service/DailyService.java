package com.ling.staservice.service;

import com.ling.staservice.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-05-06
 */
public interface DailyService extends IService<Daily> {
    //统计某一天注册人数
    void createStatisticsByDay(String day);

    //图表显示，返回两部分数据，日期json数组，数量json数组
    Map<String, Object> getShowData(String type, String begin, String end);
}
