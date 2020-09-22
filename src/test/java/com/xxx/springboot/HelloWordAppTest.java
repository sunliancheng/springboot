package com.xxx.springboot;

import com.xxx.springboot.bean.Person;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWordAppTest extends TestCase {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    Person person;

    @Autowired
    ApplicationContext ioc;

    @Test
    public void contextLoads() {
        System.out.println(ioc.getBean("helloService"));
        System.out.println(person);
    }

    @Test
    public void testLog() {
        /**
         * 由低到高
         * 可以调整日志级别，输出大于等于此级别的日志
         * springboot默认使用 info 之后的等级
         */
        logger.trace("这是trace日志");
        logger.debug("this is debug");
        logger.info("this is info");
        logger.warn("this is warn");
        logger.error("this is error");
    }

}