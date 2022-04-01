package com.ling.eduservice.service;

import com.ling.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ling.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-18
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);


    //删除章节
    boolean deleteChapter(String chapterId);

    //2、根据课程id删除章节部分
    void removeChapterByCourseId(String courseId);
}
