package com.map;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: taoxd
 * @Date: 2019/6/4 23:16
 * @Description: 启动类
 */
@SpringBootApplication
@MapperScan("com.map.dao")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
