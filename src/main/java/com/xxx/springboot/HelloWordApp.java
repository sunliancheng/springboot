package com.xxx.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;


//@ImportResource(locations = {"classpath:beans.xml"})
@SpringBootApplication
public class HelloWordApp {

    /**
     *  Spring 应用启动起来
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(HelloWordApp.class, args);
    }

    @Bean
    public ViewResolver myViewResolver() {
        return new MyViewResolver();
    }

    private static class MyViewResolver implements ViewResolver {

        @Override
        public View resolveViewName(String s, Locale locale) throws Exception {
            return null;
        }
    }
}
