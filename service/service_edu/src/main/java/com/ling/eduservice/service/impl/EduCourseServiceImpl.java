package com.ling.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ling.eduservice.entity.EduCourse;
import com.ling.eduservice.entity.EduCourseDescription;
import com.ling.eduservice.entity.frontVo.CourseFrontVo;
import com.ling.eduservice.entity.vo.CourseInfoVo;
import com.ling.eduservice.entity.vo.CoursePublishVo;
import com.ling.eduservice.entity.vo.CourseQuery;
import com.ling.eduservice.mapper.EduCourseMapper;
import com.ling.eduservice.service.EduChapterService;
import com.ling.eduservice.service.EduCourseDescriptionService;
import com.ling.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ling.eduservice.service.EduVideoService;
import com.ling.servicebase.exceptionhandler.GuliException;
import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.querydsl.QuerydslRepositoryInvokerAdapter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-18
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService chapterService;



    @Autowired
    private EduCourseDescriptionService courseDescriptionService;


    @Override
    public String savCourseInfo(CourseInfoVo courseInfoVo) {


        //向课程表添加课程基本信息
        //CourseInfoVo对象转换成EduCourse对象
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);

        if (insert <=0){
            throw new GuliException(20002,"添加失败");

        }

        //获取添加之后的课程id
        String cid = eduCourse.getId();

        //向课程简介表添加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述id也就是课程id
        eduCourseDescription.setId(cid);
        courseDescriptionService.save(eduCourseDescription);
        return cid;
    }

    //根据课程id查询课程信息并回显
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        //1.查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);




        //2.查询课程描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());


        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if (i == 0){
            throw new GuliException(20002,"修改失败了");
        }

        //修改课程描述表
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(courseInfoVo.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(courseDescription);



    }

    @Override
    public CoursePublishVo getCoursePublish(String courseId) {
        //调用mapper
        CoursePublishVo coursePublishVo = baseMapper.getCoursePublishVo(courseId);
        return coursePublishVo;
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();

        //判断条件值是否为空，如果不为空，拼接条件
        //判断是否有传入教师名
        if (!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        //判断是否传入状态
        if (!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }

        wrapper.orderByDesc("gmt_create");

        baseMapper.selectPage(pageParam, wrapper);

    }

    @Override
    public void removeCourse(String courseId) {
        //1、根据课程id删除小节
        //这里要重新写一个方法的原因是如果直接removeById()那么要传入的是video的id而并不是courseId,而因为要求是要根据courseId来删除，所以只能自己写个方法
        eduVideoService.removeVideoByCourseId(courseId);


        //2、根据课程id删除章节部分
        chapterService.removeChapterByCourseId(courseId);

        //3、根据课程id删除课程描述(因为课程描述是一一对应的关系，可以直接删除)
        courseDescriptionService.removeById(courseId);

        //4、根据课程id删除课程本身
        int i = baseMapper.deleteById(courseId);

        if (i == 0){
            throw new GuliException(20002,"删除失败");
        }

    }

    //查询8个热门课程
    @Override
    @Cacheable(key = "'selectHotCourse'",value = "course")
    public List<EduCourse> selectHotCourse() {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("view_count");
        wrapper.last("limit 8");

        return baseMapper.selectList(wrapper);
    }

    //前台多条件分页查询
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {

        String title = null;
        String subjectId = null;
        String subjectParentId = null;
        String gmtCreateSort = null;
        String buyCountSort = null;
        String priceSort = null;
        String teacherId = null;

        if (!StringUtils.isEmpty(courseFrontVo)){
            title = courseFrontVo.getTitle();
            subjectId = courseFrontVo.getSubjectId();
            subjectParentId = courseFrontVo.getSubjectParentId();
            gmtCreateSort = courseFrontVo.getGmtCreateSort();
            buyCountSort = courseFrontVo.getBuyCountSort();
            priceSort = courseFrontVo.getPriceSort();
            teacherId = courseFrontVo.getTeacherId();

        }

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，不为空拼接条件
        if (!StringUtils.isEmpty(subjectParentId)){//一级分类
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)){//二级分类
            wrapper.eq("subject_id",subjectId);
        }
        if (!StringUtils.isEmpty(buyCountSort)){//关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(priceSort)){//价格
            wrapper.orderByDesc("price");
        }
        if (!StringUtils.isEmpty(gmtCreateSort)){//最新，创建时间
            wrapper.orderByDesc("gmt_create");
        }

        baseMapper.selectList(wrapper);

        long total = pageCourse.getTotal();//总记录数
        List<EduCourse> courseList = pageCourse.getRecords();//数据集合
        long size = pageCourse.getSize();//每页记录数
        long current = pageCourse.getCurrent();//当前页
        long pages = pageCourse.getPages();//总页数
        boolean hasPrevious = pageCourse.hasPrevious();//是否有上一页
        boolean hasNext = pageCourse.hasNext();//是否有下一页


        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("list",courseList);
        map.put("size",size);
        map.put("current",current);
        map.put("pages",pages);
        map.put("hasPrevious",hasPrevious);
        map.put("hasNext",hasNext);


        return map;

    }


}
