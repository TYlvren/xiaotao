package com.cskaoyan.distributionnews;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = "com.cskaoyan.distributionnews.dao")
@EnableTransactionManagement
public class DistributionnewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributionnewsApplication.class, args);
    }

}
