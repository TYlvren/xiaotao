package com.shanyi.itemsharing.service;

import com.shanyi.itemsharing.bean.StatusBean;
import org.springframework.web.multipart.MultipartFile;


public interface UploadService {

    /**
     * 上传图片到阿里云OSS
     * @param file
     * @return
     */
    StatusBean uploadFileToAliyun(MultipartFile file);
}
