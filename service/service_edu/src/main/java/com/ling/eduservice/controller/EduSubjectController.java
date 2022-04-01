package com.ling.eduservice.controller;


import com.ling.commonutils.R;
import com.ling.eduservice.entity.subject.firstSubject;
import com.ling.eduservice.service.EduSubjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-17
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    //获取上传过来的文件，把文件的内容独取出来
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        //上传过来的文件
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }

    //课程分类列表（树形结构）
    @ApiOperation(value = "嵌套数据列表")
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<firstSubject> list = eduSubjectService.getAllSubject();
        return R.ok().data("list",list);
    }
}

