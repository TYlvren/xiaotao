package com.cskaoyan.distributionnews.filter;


import org.springframework.stereotype.Component;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class EncodingFilter  extends CharacterEncodingFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        setEncoding("UTF-8");
        super.doFilterInternal(request, response, filterChain);
    }
}
