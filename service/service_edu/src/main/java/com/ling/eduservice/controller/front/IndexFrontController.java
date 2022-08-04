package com.ling.eduservice.controller.front;

import com.ling.commonutils.R;
import com.ling.eduservice.entity.EduCourse;
import com.ling.eduservice.entity.EduTeacher;
import com.ling.eduservice.service.EduCourseService;
import com.ling.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    //查询前8条热门课程，查询前4个名师
    @GetMapping("index")
    public R index(){

        //调用查询前8热门课程的方法
        List<EduCourse> courseList = courseService.selectHotCourse();
        //查询前4张热门讲师
        List<EduTeacher> teacherList = teacherService.selectHotTeacher();

        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}
