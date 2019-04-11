package com.cskaoyan.distributionnews.controller;

import com.cskaoyan.distributionnews.bean.StatusBean;
import com.cskaoyan.distributionnews.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
public class NewsController {

    @Autowired
    private UploadService uploadService;



    /**
     * 上传news图片到阿里云
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public StatusBean upaloadAliyun(MultipartFile file) throws IOException {
        return uploadService.uploadFileToAliyun(file);
    }
}
