package com.ling.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideoAliyun(MultipartFile file);

    void removeMoreAliyunVideo(List videoIdList);
}
