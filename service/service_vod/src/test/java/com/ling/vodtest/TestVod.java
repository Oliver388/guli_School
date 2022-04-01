package com.ling.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class TestVod {
    public static void main(String[] args) throws ClientException {
        //根据视频id获取视频的播放凭证
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tLkyyNkH92n5kh8GDfp", "HfoGBjoxzy6qIBhqyf3YgL1e3vreZ4");

        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        request.setVideoId("33a6dadf10864dc2ab668c22c160685e");
        response = client.getAcsResponse(request);
        System.out.println(response.getPlayAuth());

    }

    public static void getPlayUrl() throws ClientException {
        //1 根据视频id获取视频播放地址
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tLkyyNkH92n5kh8GDfp", "HfoGBjoxzy6qIBhqyf3YgL1e3vreZ4");
        //创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        //向request对象里面设置视频id
        request.setVideoId("33a6dadf10864dc2ab668c22c160685e");

        //调用初始化对象里面的方法，传递request，获取数据
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            //PlayInfo.PlayURL = https://outin-5e688ec47b2c11ebb00500163e06123c.oss-cn-shanghai.aliyuncs.com/sv/52a57879-177f1fd5033/52a57879-177f1fd5033.mp4?Expires=1614680252&OSSAccessKeyId=LTAIrkwb21KyGjJl&Signature=FPlVfGbIDVGuvR3W8f2K4QcpATw%3D
        }

        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");//VideoBase.Title = 6 - What If I Want to Move Faster.mp4

    }
}
