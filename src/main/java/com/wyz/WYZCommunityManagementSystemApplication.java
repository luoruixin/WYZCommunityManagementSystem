package com.wyz;

import cn.hutool.core.util.StrUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.File;
import java.util.UUID;


// Generated by https://start.springboot.io
// 优质的 spring/boot/data/security/cloud 框架中文文档尽在 => https://springdoc.cn

@SpringBootApplication
//@EnableAspectJAutoProxy(exposeProxy = true) //暴露代理对象
@EnableTransactionManagement//开启事务注解的支持
@EnableCaching   //开启cache
public class WYZCommunityManagementSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(WYZCommunityManagementSystemApplication.class, args);
    }
}
