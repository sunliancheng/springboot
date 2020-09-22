package com.xxx.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@ImportResource(locations = {"classpath:beans.xml"})
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
