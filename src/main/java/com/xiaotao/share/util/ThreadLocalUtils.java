package com.xiaotao.share.util;

import com.xiaotao.share.model.User;

public class ThreadLocalUtils {
    //保证threadLocal是同一个对象，
    // 这样在其内部类ThreadLocalMap中键将一样，
    // 只是不同的线程ThreadLocalMap不一样
    private static ThreadLocal<User> threadLocal = new ThreadLocal<>();


    public static User get(){
        return threadLocal.get();
    }

    public static void set(User user){
        threadLocal.set(user);
    }

}
