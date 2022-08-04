package com.ling.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ling.commonutils.R;
import com.ling.eduservice.entity.EduCourse;
import com.ling.eduservice.entity.vo.CourseInfoVo;
import com.ling.eduservice.entity.vo.CoursePublishVo;
import com.ling.eduservice.entity.vo.CourseQuery;
import com.ling.eduservice.service.EduChapterService;
import com.ling.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-18
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    //课程列表
    @GetMapping()
    public R getCourseList(){
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list",list);
    }


    //TODO 完善条件查询带分页
    @PostMapping("PageCourseCondition/{current}/{size}")
    public R PageCourseCondition(@ApiParam(name = "current", value = "当前页码", required = true)@PathVariable long current,
                                 @ApiParam(name = "size", value = "每页记录数", required = true)@PathVariable long size,
                                 @RequestBody(required = false) CourseQuery courseQuery){

        //创建分页page对象
        Page<EduCourse> pageParam = new Page<>(current, size);

        //调用方法实现多条件分页查询
        courseService.pageQuery(pageParam, courseQuery);

        //获取查询到的数据
        List<EduCourse> records = pageParam.getRecords();


        //获取总记录数
        long total = pageParam.getTotal();

        return R.ok().data("total",total).data("rows",records);


    }





    //添加课程基本信息的方法
    @PostMapping("/addCourseInfo")
    public R addCourse(@RequestBody CourseInfoVo courseInfoVo){
        String id = courseService.savCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }


    //根据课程id查询课程信息并回显
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("/getCoursePublishInfo/{courseId}")
    public R getCoursePublishInfo(@PathVariable String courseId){
        CoursePublishVo coursePublishVo = courseService.getCoursePublish(courseId);
        return R.ok().data("coursePublish",coursePublishVo);
    }

    //课程最终发布
    //修改课程状态
    @PostMapping("publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");//设置课程发布的状态
        courseService.updateById(eduCourse);
        return R.ok();
    }


    //删除课程
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return R.ok();
    }


}

