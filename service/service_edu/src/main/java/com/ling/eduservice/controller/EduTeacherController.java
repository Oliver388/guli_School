package com.ling.eduservice.controller;




import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ling.commonutils.R;
import com.ling.eduservice.entity.EduTeacher;
import com.ling.eduservice.entity.vo.TeacherQuery;
import com.ling.eduservice.service.EduTeacherService;
import com.ling.servicebase.exceptionhandler.GuliException;
import org.omg.PortableInterceptor.LOCATION_FORWARD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-05
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {
    //把service注入
    @Autowired
    private EduTeacherService teacherService;


    //1 查询讲师表所有数据
    //rest风格
    @GetMapping("findAll")
    public R findAllTeacher(){
        //调用service的方法实现查询所有
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }


    //删除
    @DeleteMapping("{id}")
    public R removeTeacher(@PathVariable String id){
        boolean b = teacherService.removeById(id);
        if (b){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //分页查询讲师的方法
    @GetMapping("pageTeacher/{current}/{size}")
    public R pageListTeacher(@PathVariable long current,@PathVariable long size){

        //创建page对象
        Page<EduTeacher> page = new Page<>(current,size);

        try{
            int i = 10/0;
        }catch (Exception e){
            throw new GuliException(20001,"执行了自定义异常处理。。。");
        }

        //调用方法实现分页,底层封装，把分页所有数据封装到page对象里面
        teacherService.page(page,null);
        long total = page.getTotal();//总记录数
        List<EduTeacher> records = page.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }

    //条件查询带分页
    @PostMapping("PageTeacherCondition/{current}/{size}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long size,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> eduTeacherPage = new Page<>(current,size);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件组合查询
        //动态sql
        //判断条件值是否为空，拼接条件
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }

        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }

        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }

        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }

        //排序
        wrapper.orderByDesc("gmt_create");
        //调用方法实现分页
        teacherService.page(eduTeacherPage,wrapper);
        long total = eduTeacherPage.getTotal();
        List<EduTeacher> records = eduTeacherPage.getRecords();
        return R.ok().data("total",total).data("records",records);
    }

    //添加讲师
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //根据讲师id查询
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher e = teacherService.getById(id);
        return R.ok().data("teacher",e);
    }

    //讲师修改
    @PostMapping("modifyTeacher")
    public R modifyTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = teacherService.updateById(eduTeacher);
        if (b){
            return R.ok();
        }else {
            return R.error();
        }
    }

}

