package com.xxx.springboot.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;


/**
 *  告诉springboot本类中的所有属性和配置文件中相关配置绑定
 *  只有这个组件在容器中，才能使用容器提供的ConfigurationProperties
 */
@Component
@PropertySource(value = {"classpath:person.properties"})
@ConfigurationProperties(prefix = "person")
@Validated
public class Person {
    //@Value("${person.age}")
    private int age;
    //@Email
    //@Value("${person.lastName}")
    private String lastName;

    public void setAge(int age) {
        this.age = age;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + lastName + '\'' +
                '}';
    }
}
