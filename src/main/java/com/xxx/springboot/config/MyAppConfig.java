package com.xxx.springboot.config;

import com.xxx.springboot.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 指明这是一个配置类，替代spring配置文件
 */
@Configuration
public class MyAppConfig {

    //将方法的返回值添加到容器中；容器中这个组件 默认id 就是这个方法名
    @Bean
    public HelloService helloService() {
        return new HelloService();
    }

}
