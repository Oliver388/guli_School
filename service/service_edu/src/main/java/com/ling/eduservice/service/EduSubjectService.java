package com.ling.eduservice.service;

import com.ling.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ling.eduservice.entity.subject.firstSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-17
 */

public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService eduSubjectService);


    List<firstSubject> getAllSubject();
}
