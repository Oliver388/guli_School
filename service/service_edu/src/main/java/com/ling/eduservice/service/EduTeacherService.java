package com.ling.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ling.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-05
 */
public interface EduTeacherService extends IService<EduTeacher> {

    List<EduTeacher> selectHotTeacher();

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
