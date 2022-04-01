package com.ling.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ling.eduservice.entity.EduTeacher;
import com.ling.eduservice.mapper.EduTeacherMapper;
import com.ling.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microsoft.schemas.office.visio.x2012.main.PageType;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-05
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    //查询前4个热门讲师
    @Override
    @Cacheable(key = "'selectHotTeacher'",value = "teacher")
    public List<EduTeacher> selectHotTeacher() {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 4");

        return baseMapper.selectList(wrapper);
    }

    //前台系统分页查询讲师的方法
    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");

        //把分页数据封装到pageTeacher对象中
        baseMapper.selectPage(pageTeacher,wrapper);

        //把分页的数据获取出来返回一个map集合
        HashMap<String,Object> map = new HashMap<>();

        //总记录数
        long total = pageTeacher.getTotal();
        //当前页
        long current = pageTeacher.getCurrent();
        //每页记录数
        long size = pageTeacher.getSize();
        //查询到的对象
        List<EduTeacher> teacherList = pageTeacher.getRecords();
        //总页数
        long pages = pageTeacher.getPages();
        //是否有上一页
        boolean hasPrevious = pageTeacher.hasPrevious();
        //是否有下一页
        boolean hasNext = pageTeacher.hasNext();


        //将数据封装到map中返回
        map.put("total",total);
        map.put("current",current);
        map.put("size",size);
        map.put("list",teacherList);
        map.put("hasPrevious",hasPrevious);
        map.put("hasNext",hasNext);
        map.put("pages",pages);
        return map;
    }
}
