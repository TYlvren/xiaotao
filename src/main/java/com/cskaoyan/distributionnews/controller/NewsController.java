package com.cskaoyan.distributionnews.controller;

import com.cskaoyan.distributionnews.bean.StatusBean;
import com.cskaoyan.distributionnews.model.New;
import com.cskaoyan.distributionnews.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    @RequestMapping("/")
    public String toHeadPage(Model model) {
        List<New> news = newsService.findNew();
        model.addAttribute("news", news);
        return "home";
    }

    @Value("${image-dir}")
    String dir;

    /**
     * 上传news图片
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public StatusBean uploadImage(MultipartFile file, HttpServletRequest request) {
        String originalFilename = file.getOriginalFilename();
        String newFilename = Long.toHexString(System.currentTimeMillis()) + originalFilename;
        File saveFile = new File(dir + newFilename);
        StatusBean statusBean = new StatusBean();
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();

            statusBean.setMsg("上传异常");
            statusBean.setCode(1);
            return statusBean;
        }

        String url = request.getScheme() + "://" + request.getServerName() + "/" + request.getContextPath();
        String imgurl = url + "image?name=" + newFilename;
        statusBean.setMsg(imgurl);
        statusBean.setCode(0);
        return statusBean;
    }

    @RequestMapping("image")
    @ResponseBody
    public byte[] showImage(String name) throws IOException {
        File file = new File(dir + name);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[1024 * 1024 * 5];
        int read = inputStream.read(bytes);
        return bytes;
    }


}
