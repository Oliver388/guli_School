package com.ling.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ling.eduservice.entity.EduSubject;
import com.ling.eduservice.entity.excel.SubjectData;
import com.ling.eduservice.entity.subject.firstSubject;
import com.ling.eduservice.entity.subject.secondSubject;
import com.ling.eduservice.listener.SubjectExcelListener;
import com.ling.eduservice.mapper.EduSubjectMapper;
import com.ling.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-17
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        //输入流会有异常
        try{
            InputStream inputStream = file.getInputStream();

            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<firstSubject> getAllSubject() {
        //查询所有一级分类 parent_id=0
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("parent_id","0");
        List<EduSubject> list1 = this.list(wrapper1);

        //查询所有二级分类 parent_id!=0
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper2.ne("parent_id","0");
        List<EduSubject> list2 = this.list(wrapper2);

        //创建list集合，用于封装最终数据
        List<firstSubject> finalSubjectList = new ArrayList<>();

        //封装一级分类
        //查询出来所有的一级分类list集合遍历，得到每个一级分类对象，获取每个一级分类对象值
        //封装到要求的最终list集合中
        for (int i = 0; i < list1.size(); i++) {
            //得到list1中每个eduSubject对象
            EduSubject eduSubject1 = list1.get(i);

            firstSubject firstSubject = new firstSubject();

            BeanUtils.copyProperties(eduSubject1,firstSubject);

            finalSubjectList.add(firstSubject);

            //在一级分类循环遍历查询所有的二级分类
            //创建list集合封装每个一级分类的二级分类
            List<secondSubject> finalTwoSubjects = new ArrayList<>();

            for (int j=0;j<list2.size();j++){
                EduSubject eduSubject2 = list2.get(j);

                //因为这是二级分类，所以要判断该类的parent_id和一级的id是否一样，一样才能进行下一步
                if (eduSubject2.getParentId().equals(firstSubject.getId())){
                    //把tSubject值复制到TwoSubject，最终放在twoSubjectsList中
                    secondSubject secondSubject = new secondSubject();
                    BeanUtils.copyProperties(eduSubject2,secondSubject);
                    finalTwoSubjects.add(secondSubject);

                }
            }

            firstSubject.setChildren(finalTwoSubjects);


        }





        //封装二级分类
        return finalSubjectList;
    }


}
