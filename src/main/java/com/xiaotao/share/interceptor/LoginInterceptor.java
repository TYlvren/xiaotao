package com.xiaotao.share.interceptor;


import com.xiaotao.share.model.User;
import com.xiaotao.share.service.MessageService;
import com.xiaotao.share.util.ThreadLocalUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//拦截器加载是在springcontext创建之前完成的
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private Logger logger;

    @Autowired
    private MessageService messageService;

    private final String[] ignoreUrl = new String[]
            {
                    "/","/categorySearch", "/fuzzySearch", "/login", "/reg", "/like", "/dislike"
            };
    private final String[] staticResources = new String[]
            {
                    "/goods/","/fonts", "/image", "/images", "/scripts",
                    "/styles", "/error",
            };
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();
        logger.info("Url =" + servletPath);

        String name = Thread.currentThread().getName();
        logger.info("LoginInterceptor线程:" + name);

        HttpSession session = request.getSession();
        Object attribute = session.getAttribute("user");
        User user;

        //已经登录
        if (attribute != null) {
            //将user方入ThreadLocalMap
            user = (User) attribute;
            ThreadLocalUtils.set(user);
            logger.info("LoginInterceptor用户：" + ThreadLocalUtils.get());

            //将未读消息放入request
            int unread = messageService.findCountUnread(user.getId());
            request.setAttribute("unreadMessage", unread);
            return true;
        }

        //静态资源及Rest风格中不用登陆的url放行
        for (String resource : staticResources) {
            if(servletPath.contains(resource)){
                return true;
            }
        }

        //不用登陆即可访问的url放行
        for (String ignore : ignoreUrl) {
            if (servletPath.equals(ignore)) {
                return true;
            }
        }

        request.setAttribute("pop",1);
        request.getRequestDispatcher("/").forward(request,response);
        return false;
    }
}
