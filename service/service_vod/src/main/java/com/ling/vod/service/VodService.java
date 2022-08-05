package com.ling.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    //阿里云上传视频
    String uploadVideoAliyun(MultipartFile file);

    //根据小节id删除一个视频
    void removeMoreAliyunVideo(List videoIdList);

    //根据视频id获取视频凭证
    String getPlayAuth(String id);
}
