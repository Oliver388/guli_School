package com.ling.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ling.eduservice.entity.EduSubject;
import com.ling.eduservice.entity.excel.SubjectData;
import com.ling.eduservice.service.EduSubjectService;
import com.ling.servicebase.exceptionhandler.GuliException;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {


    //因为SubjectExcelListener是不能交给spring进行管理的，需要自己手动去new，不能注入其他对象
    //不能实现数据库操作
    public EduSubjectService subjectService;

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public SubjectExcelListener() {

    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null){
            throw new GuliException(20001,"文件数据为空");

        }

        //一行一行读取，每次读取有两个值，第一个值一级分类，第二个值二级分类
        //添加一级分类
        EduSubject first = this.existFirstCondition(subjectService, subjectData.getFirstCondition());
        if (first == null){
            first = new EduSubject();
            first.setParentId("0");
            first.setId(subjectData.getFirstCondition());
            subjectService.save(first);
        }

        //获取一级分类的id值
        String pid = first.getId();
        //添加二级分类
        EduSubject second = this.existSecondCondition(subjectService, subjectData.getSecondCondition(),pid);
        if (second == null){
            second = new EduSubject();
            second.setParentId(pid);
            second.setTitle(subjectData.getSecondCondition());
            subjectService.save(second);
        }
    }




    //判断一级分类不能重复添加
    private EduSubject existFirstCondition(EduSubjectService eduSubjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",'0');
        EduSubject one = eduSubjectService.getOne(wrapper);
        return one;
    }



    //判断二级分类不能重复添加
    private EduSubject existSecondCondition(EduSubjectService eduSubjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject two = eduSubjectService.getOne(wrapper);
        return two;
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
