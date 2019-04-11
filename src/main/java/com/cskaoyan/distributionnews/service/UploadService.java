package com.cskaoyan.distributionnews.service;

import com.cskaoyan.distributionnews.bean.StatusBean;
import org.springframework.web.multipart.MultipartFile;


public interface UploadService {

    /**
     * 上传图片到阿里云OSS
     * @param file
     * @return
     */
    StatusBean uploadFileToAliyun(MultipartFile file);
}
