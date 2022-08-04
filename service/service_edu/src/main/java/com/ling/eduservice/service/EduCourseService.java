package com.ling.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ling.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ling.eduservice.entity.frontVo.CourseQueryVo;
import com.ling.eduservice.entity.frontVo.CourseWebVo;
import com.ling.eduservice.entity.vo.CourseInfoVo;
import com.ling.eduservice.entity.vo.CoursePublishVo;
import com.ling.eduservice.entity.vo.CourseQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-18
 */
public interface EduCourseService extends IService<EduCourse> {

    //添加课程基本信息的方法
    String savCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id查询课程基本信息
    CourseInfoVo getCourseInfo(String courseId);

    //修改课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id查询课程确认信息
    CoursePublishVo getCoursePublish(String courseId);

    //多条件查询讲师带分页
    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    //删除课程
    void removeCourse(String courseId);

    //查询8个热门课程
    List<EduCourse> selectHotCourse();

    //前台多条件分页查询
    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseQueryVo courseQueryVo);

    //根据讲师id查询讲师所讲课程列表
    List<EduCourse> selectByTeacherId(String teacherId);

    /**
     * 获取课程信息
     * @param id
     * @return
     */
    CourseWebVo selectInfoWebById(String id);

    /**
     * 更新课程浏览数
     * @param id
     */
    void updatePageViewCount(String id);
}
