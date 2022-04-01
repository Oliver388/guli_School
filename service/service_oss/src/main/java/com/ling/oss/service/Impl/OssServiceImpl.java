package com.ling.oss.service.Impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.ling.oss.service.OssService;
import com.ling.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    //上传文件到oss
    @Override
    public String upLoadFileAvatar(MultipartFile file) {
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            //创建oss实例
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            //获取上传文件的输入流
            InputStream inputStream = file.getInputStream();

            //获取文件名称
            String fileName = file.getOriginalFilename();

            //在文件名称里百添加随机唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            fileName = uuid+fileName;

            //把文件按照日期进行分类
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = datePath + "/" + fileName;

            //调用oss方法实现上传
            //第一个参数是bucketName
            //第二个参数是fileName
            //第三个参数是获取的上传文件的输入流
            ossClient.putObject(bucketName,fileName,inputStream);

            //关闭ossclient
            ossClient.shutdown();

            //返回图片的路径
            //需要把链接手动拼接出来
            String url = "https://" + bucketName + "." +endpoint + "/" + fileName;
            return url;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }



    }
}
