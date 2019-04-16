package com.shanyi.itemsharing;

import com.shanyi.itemsharing.bean.StatusBean;
import com.shanyi.itemsharing.util.MD5Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DistributionnewsApplicationTests {


    @Autowired
    private StatusBean statusBean;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testSpringBean() {

        logger.info("引用前： " + statusBean.toString());

        StatusBean statusBean1 = this.statusBean;
        statusBean1.setCode(2);
        logger.info("引用时改变值： " + statusBean1.toString());

        statusBean1 = new StatusBean();

        logger.info("引用后： " + statusBean.toString());
    }


    public String getMD5(String pwd) {

        String md5hashvalue = "";

        try {
            //一个长度可扩展的String
            StringBuffer stringBuffer = new StringBuffer();

            //1、传一个参数：algorithm 算法
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            //2、****MD5值生成算法，传入密码，返回一个MD5字节数组(16字节长度)**** 16*8=128
            byte[] digest = md5.digest(pwd.getBytes());//要先将输入的密码字符串变成字节数组

            //3、对结果进行处理： 将MD5值（16个字节的字节数组）还原成字符串
            for (byte b : digest) {
                //从MD5字节数组中一个字节一个字节拿出来，放入StringBuffer中

                //3.1 将byte变为int （一个字节对应一个两位的16进制数）
                int i = b & 0x000000FF;
                //3.2 int转为16进制的字符串（将一个两位数的16进制数 转为 一个字节）

                int value = 10;//(盐值)

                String s = Integer.toHexString(i + value);//要求传一个int值

                //00001111  15  F

                //3.3 将每个字节对应的字符串一次放入StringBuffer 中
                if (s.length() == 1) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(s);
            }
            md5hashvalue = stringBuffer.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //返回一个MD5 hash值
        return md5hashvalue;
    }


    @Test
    public void testMD5(){
        String md5 = MD5Utils.getMD5("md5md5", Integer.valueOf("13",16));

        assert md5.equals("4375f4f42f77a6ab3707055c9c22846a");

    }

    @Test
    public void testMap(){
        /*Map<String,Integer> unreadMap = new HashMap<>();
        Integer integer = unreadMap.get("11");
        integer ++;
        System.out.println(integer);*/
    }

}

