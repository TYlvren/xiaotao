package com.cskaoyan.distributionnews.service.impl;

import com.aliyun.oss.OSSClient;
import com.cskaoyan.distributionnews.bean.StatusBean;
import com.cskaoyan.distributionnews.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {

    private final StatusBean statusBean;

    @Autowired
    public UploadServiceImpl(StatusBean statusBean) {
        this.statusBean = statusBean;
    }

    /**
     * 上传图片到阿里云OSS
     *
     * @param file
     * @return
     */
    @Override
    public StatusBean uploadFileToAliyun(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。
        // 强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAIp7WJZjLwzPwd";
        String accessKeySecret = "f9QDMgVp1jXxDBa4KhTQgH1Ok7SrQW";

        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        String content = "Hello OSS";

        //获取当前日期字符串
        long timeMillis = System.currentTimeMillis();
        Date date = new Date(timeMillis);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);

        //获取上传文件名
        String originalFilename = file.getOriginalFilename();
        UUID uuid = UUID.randomUUID();
        String newFilename = format + "/" + uuid + "_" + originalFilename;

        //OSS中文件夹的概念仅是一个逻辑概念，定义object的key为abc/1.jpg就会在该bucket下创建一个abc的文件夹
        String bucketName = "distributionnews";

        try {
            ossClient.putObject(bucketName, newFilename, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            statusBean.setMsg("上传异常");
            statusBean.setCode(1);
            return statusBean;
        }

        // 关闭OSSClient。
        ossClient.shutdown();

        //以缩略图的形式显示
        String msg = "https://" + bucketName + ".oss-cn-hangzhou.aliyuncs.com/" + newFilename + "?x-oss-process=image/resize,w_120,h_100";
        statusBean.setMsg(msg);
        statusBean.setCode(0);
        return statusBean;
    }
}
