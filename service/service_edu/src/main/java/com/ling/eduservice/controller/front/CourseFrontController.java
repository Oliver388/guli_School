package com.ling.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ling.commonutils.R;
import com.ling.eduservice.entity.EduChapter;
import com.ling.eduservice.entity.EduCourse;
import com.ling.eduservice.entity.chapter.ChapterVo;
import com.ling.eduservice.entity.frontVo.CourseQueryVo;
import com.ling.eduservice.entity.frontVo.CourseWebVo;
import com.ling.eduservice.service.EduChapterService;
import com.ling.eduservice.service.EduCourseService;
import com.ling.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("eduservice/coursefront")
public class CourseFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    //前台多条件分页查询
    @ApiOperation(value = "分页课程列表")
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false) CourseQueryVo courseQueryVo){
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFrontList(pageCourse, courseQueryVo);
        return R.ok().data(map);
    }

    //前台根据ID查询课程信息
    @ApiOperation(value = "根据ID查询课程")
    @GetMapping(value = "{courseId}")
    public R getById(
            @ApiParam(name = "courseId", value = "课程ID", readOnly = true)
            @PathVariable String courseId){

        //查询课程信息和讲师信息
        CourseWebVo courseWebVo = courseService.selectInfoWebById(courseId);

        //查询当前课程的章节信息
        List<ChapterVo> chapterVoList = chapterService.getChapterVideoByCourseId(courseId);

        return R.ok().data("course",courseWebVo).data("chapterVoList",chapterVoList);
    }

}
