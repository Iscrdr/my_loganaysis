package com.qianfeng.bigdata;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = "com.qianfeng.bigdata.*")
public class LogAppDriver{


    public static void main(String[] args) {
        SpringApplication.run(LogAppDriver.class, args);
    }
}
