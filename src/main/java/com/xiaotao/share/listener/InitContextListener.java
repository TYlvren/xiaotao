package com.xiaotao.share.listener;

import com.xiaotao.share.model.Category;
import com.xiaotao.share.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

@Component
public class InitContextListener implements ServletContextListener {

    @Autowired
    private CategoryService categoryService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        //将应用名加入全局变量
        String path = servletContext.getContextPath();
        servletContext.setAttribute("contextPath",path);
        //将分类加入全局变量
        List<Category> categories = categoryService.findAll();
        servletContext.setAttribute("categories",categories);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
