package com.ling.eduservice.mapper;

import com.ling.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ling.eduservice.entity.frontVo.CourseWebVo;
import com.ling.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-03-18
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getCoursePublishVo(String courseId);

    CourseWebVo selectInfoWebById(String courseId);


}
