package com.ling.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ling.commonutils.R;
import com.ling.eduservice.entity.EduCourse;
import com.ling.eduservice.entity.EduTeacher;
import com.ling.eduservice.service.EduCourseService;
import com.ling.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherIndexFront {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    //分页查询讲师的方法
    @ApiOperation(value = "分页讲师列表")
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable long limit){
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        Map<String,Object> map = teacherService.getTeacherFrontList(pageTeacher);
        //返回分页所有的数据
        return R.ok().data(map);

    }

    //根据id查询讲师信息（讲师本身信息+讲师所讲课程信息）
    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String teacherId){
        //根据id获取讲师
        EduTeacher teacher = teacherService.getById(teacherId);

        //根据id获取讲师所讲的课程信息
        List<EduCourse> courseList = courseService.selectByTeacherId(teacherId);

        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }


}
