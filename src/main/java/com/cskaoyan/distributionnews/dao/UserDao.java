package com.cskaoyan.distributionnews.dao;

import com.cskaoyan.distributionnews.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {


    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    User selectUserByUsername(String username);

    /**
     * 通过用户名和密码查找用户
     * @param username
     * @param password
     * @return
     */
    User selectUserByUsernameAndPassword(@Param("username") String username,@Param("password") String password);

    /**
     * 通过id查询User
     * @param id
     * @return
     */
    User selectUserById(int id);


    /**
     * 向数据库插入用户
     * @param user
     */
    int insertUser(User user);


    String selectSaltByUsername(String username);

    int selectUserIdByUsername(String username);
}
