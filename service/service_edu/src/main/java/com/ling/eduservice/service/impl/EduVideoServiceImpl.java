package com.ling.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ling.eduservice.client.VodClient;
import com.ling.eduservice.entity.EduVideo;
import com.ling.eduservice.mapper.EduVideoMapper;
import com.ling.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-18
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {


    //注入vodclient
    @Autowired
    private VodClient vodClient;
    
    //1、根据课程id删除小节
    //TODO 删除小节的时候还要删除对应的视频
    @Override
    public void removeVideoByCourseId(String courseId) {
        //根据课程id查询所有的视频id
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(wrapperVideo);

        List<String> videoIds = new ArrayList<>();
        for (EduVideo eduVideo : eduVideos) {
            String videoSourceId = eduVideo.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)){
                videoIds.add(videoSourceId);
            }
        }

        if (videoIds.size()>0){
            vodClient.deleteBatch(videoIds);
        }


        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
