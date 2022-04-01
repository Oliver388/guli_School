package com.ling.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ling.eduservice.entity.EduChapter;
import com.ling.eduservice.entity.EduVideo;
import com.ling.eduservice.entity.chapter.ChapterVo;
import com.ling.eduservice.entity.chapter.VideoVo;
import com.ling.eduservice.mapper.EduChapterMapper;
import com.ling.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ling.eduservice.service.EduVideoService;
import com.ling.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-18
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;//因为小节在章节中没法查询，因此注入video

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        //根据课程id查询出章节的数据
        QueryWrapper<EduChapter> wrapper1 = new QueryWrapper();
        wrapper1.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapper1);


        //根据课程id和章节id查询出小节
        QueryWrapper<EduVideo> wrapper2 = new QueryWrapper();
        wrapper2.eq("course_id",courseId);
        List<EduVideo> list = videoService.list(wrapper2);

        //创建list
        ArrayList<ChapterVo> finalList = new ArrayList<>();

        //遍历查询出来的章节集合用list封装
        for (int i = 0; i < eduChapters.size(); i++) {
            EduChapter Chapter = eduChapters.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(Chapter,chapterVo);
            finalList.add(chapterVo);


            ArrayList<VideoVo> finalList2 = new ArrayList<>();
            for(int j = 0; j <list.size();j++){
                EduVideo eduVideo = list.get(j);
                if (Chapter.getId().equals(eduVideo.getChapterId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    finalList2.add(videoVo);
                }
            }

            chapterVo.setChildren(finalList2);
        }

        //遍历查询出来的小节在封装
        return finalList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapterid章节id查询小节表，如果查到数据，不进行删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        if (count > 0){//能查出小节，不进行删除
            throw new GuliException(20001,"不能删除");
        }else {//查不出小节，进行删除
            //删除章节
            boolean b = this.removeById(chapterId);
            return b;
        }

    }

    //2、根据课程id删除章节部分
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
