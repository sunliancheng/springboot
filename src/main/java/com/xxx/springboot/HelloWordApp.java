package com.xxx.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class HelloWordApp {

    /**
     *  Spring 应用启动起来
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(HelloWordApp.class, args);
    }
}
