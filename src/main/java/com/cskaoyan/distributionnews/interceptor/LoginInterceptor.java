package com.cskaoyan.distributionnews.interceptor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final String[] ignoreUrl = new String[]{"/fonts","/login","/image","/images","/scripts",
            "/styles","/error","/reg","logout","/news/","like","dislike"};
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();
        logger.info("Url =" + servletPath);

        if("/".equals(servletPath)){
            return true;
        }

        for (String ignore : ignoreUrl) {
            if(servletPath.contains(ignore)){
                return true;
            }
        }
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("user") == null){
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}
