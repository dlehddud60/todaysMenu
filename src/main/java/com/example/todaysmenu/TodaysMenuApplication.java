package com.example.todaysmenu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.example.todaysmenu")
public class TodaysMenuApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodaysMenuApplication.class, args);
    }

}

