package com.cskaoyan.distributionnews.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    /**
     * 将密码使用MD5加密
     * @param password
     * @return
     */
    public static String getMD5(String password,int salt) {

        StringBuilder sb = new StringBuilder();

        //传入信息加密的算法，生成算法类的实例
        MessageDigest instance = null;
        try {
            instance = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //MD5算法生成16个字节(128位)的哈希值，需要一个字节数组
        assert instance != null;
        byte[] digest = instance.digest(password.getBytes());


        //将字节数组按16进制转化为字符串,128位16字节将转化为长度为32的字符串，
        // 因为直接保存字节流到数据库不方便
        for (byte b : digest) {

            //需要将数字转化为16进制字符串，就需要用到Integer的的方法，所以要将byte先转化为int，
            // 但是需要保留b的符号位，所以进行与操作，利用补码进行计算
            int i = b & 0x000000FF;
            String s = Integer.toHexString(i + salt);

            int l = s.length();
            //如果b前四位都为0，转化成字符串后，长度只有一位，需将这个0补齐
            if(l == 1){
                sb.append("0");
                //拼接字符串
                sb.append(s);
            }else if(l > 2){   //如果i+salt超过了255，转化成字符串后长度就会超过2位，取后两位即可
                String substring = s.substring(l-2,l);
                sb.append(substring);
            }else {
                sb.append(s);
            }

        }

        //返回md5的HashCode
        //Logger logger = LoggerFactory.getLogger(MD5Utils.class);
       // logger.info("md5长度为： "+ md5HashCode.length());
        return sb.toString();
    }
}
