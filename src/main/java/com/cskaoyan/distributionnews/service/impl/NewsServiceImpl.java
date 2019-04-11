package com.cskaoyan.distributionnews.service.impl;

import com.aliyun.oss.OSSClient;
import com.cskaoyan.distributionnews.bean.StatusBean;
import com.cskaoyan.distributionnews.bean.StatusBeanUser;
import com.cskaoyan.distributionnews.dao.NewDao;
import com.cskaoyan.distributionnews.dao.UserDao;
import com.cskaoyan.distributionnews.model.New;
import com.cskaoyan.distributionnews.model.User;
import com.cskaoyan.distributionnews.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private NewDao newsDao;

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    @Override
    public StatusBeanUser registerUser(User user) {
        User userByUsername = userDao.selectUserByUsername(user.getUsername());
        StatusBeanUser statusBean = new StatusBeanUser();
        if(userByUsername != null){
            statusBean.setCode(1);
            statusBean.setMsgname("用户名已被注册");
            return statusBean;
        }
        int random = (int) (Math.random() * 10 + 1);
        user.setHeadUrl("images/headImg/"+ random +".jpg");
        int i = userDao.insertUser(user);
        if(i != 1){
            statusBean.setCode(2);
            statusBean.setMsgname("注册异常");
            return statusBean;
        }

        statusBean.setCode(0);
        statusBean.setMsgname("注册成功");
        return statusBean;
    }


    /**
     * 登录用户
     *
     * @param user
     * @return
     */
    @Override
    public StatusBeanUser loginUser(User user, HttpSession session) {
        User userByUsername = userDao.selectUserByUsername(user.getUsername());
        StatusBeanUser statusBean = new StatusBeanUser();
        if(userByUsername == null){
            statusBean.setCode(1);
            statusBean.setMsgname("用户名不存在");
            return statusBean;
        }

        User userByUsernameAndPassword = userDao.selectUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (userByUsernameAndPassword == null){
            statusBean.setCode(2);
            statusBean.setMsgname("密码错误");
            return statusBean;
        }

        session.setAttribute("user",userByUsernameAndPassword);
        statusBean.setCode(0);
        statusBean.setMsgname("登录成功");
        return statusBean;

    }


    /**
     * 添加news
     *
     * @param news
     */
    @Override
    public StatusBean addNews(New news) {
        int i = newsDao.insertNews(news);
        StatusBean statusBean = new StatusBean();
        if(i != 1){
            statusBean.setCode(2);
            statusBean.setMsg("异常");
            return statusBean;
        }

        statusBean.setCode(0);
        statusBean.setMsg("成功");
        return statusBean;
    }

    /**
     * 查找所有的new
     *
     * @return
     */
    @Override
    public List<New> findNew() {
        return newsDao.selectAllNew();
    }

    /**
     * 通过id查找User
     *
     * @param id
     * @return
     */
    @Override
    public User findUser(int id) {
        return userDao.selectUserById(id);
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

        StatusBean statusBean = new StatusBean();
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

        String msg = "https://" + bucketName + ".oss-cn-hangzhou.aliyuncs.com/" + newFilename;
        statusBean.setMsg(msg);
        statusBean.setCode(0);
        return statusBean;
    }
}
