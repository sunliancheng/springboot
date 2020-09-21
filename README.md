# Springboot

[本实例代码位于github：](https://github.com/sunliancheng/springboot)

## Springboot 入门

### 微服务

把每个功能元素独立出来，动态扩展。一个应用是一个小型服务，通过HTTP的方式进行互通。

与之对应的是 All in one：所有都写在一个代码中



### 创建maven工程

### 倒入springboot依赖

maven 配置文件

```maven
    <parent>
        <artifactId>spring-boot-starter-parent</artifactId>
        <groupId>org.springframework.boot</groupId>
        <version>2.3.2.RELEASE</version>
    </parent>
    
    <dependencies>
        <!-- web application dependency-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

```

### 编写主程序

```java
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
```

### 编写相关Controller 和 Service

```java
@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}

```

### 运行程序测试 与 部署

创建一个可执行的jar包：

把maven插件依赖倒入

```bash
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>2.3.2.RELEASE</version>
            </plugin>
        </plugins>
    </build>
```

通过maven-lifecycle-package，打包项目成jar包，然后部署到linux系统上。使用java -jar指令。

springboot-starter-web: 保证web模块正常运行所需要的包

### 主程序类

@SpringBootApplication: 这个是Springboot的主配置类，Springboot运行这个类的main方法来启动Springboot应用

@SpringBootConfiguration: Springboot配置类，@Configuration：配置类替换配置文件。配置类也是容器中的组件，@Component

@EnableAutoConfiguration：开启自动配置，以前需要配置的东西，springboot帮忙自动配置

```java
@AutoConfigurationPackage
@Import(AutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {
```

@AutoConfigurationPackage: 自动配置包		     		

​		@Import(AutoConfigurationPackage.Register.class) :
​		Spring的底层注解@Import，给容器中导入一个组件（AutoConfigurationPackage.Register.class)

​		将主配置类（@SpringbootApplication）的所在包下面的所有组件扫描到容器中.

​		@Import(EnableAutoConfigurationImportSelector.class);

​		EnableAutoConfigurationImportSelector.class: 将所有需要导入的组件以全类名的方式放回。这些组件会被添加到容器中。

​		会给容器中导入很多的自动配置类（xxxAutoConfiguration），把这个场景所需要的所有组件导入容器中，并且配置好。

​		SpringFacotriesLoader.loadFactoryNames(EnableAutoConfiguration.class.classLoader); Springboot在启动的时候从类路径下的META-INF/spring.factories中获取EnableAutoConfiguration指定的值，将这些值作为自动配置类导入到容器中，自动配置类就生效。

​		J2EE的整体整合解决方案和自动配置都在 org/springframework/boot/spring-boot-autoconfigure 中

### 使用Spring Initializer快速创建Spring Boot项目

IDEA new project ----- Spring Initializer 来创建

* static 文件夹，保存所有静态资源，js css images
* templates 保存所有的模版页面，使用嵌入式Tomcat，不支持jsp
* application.properties springboot应用的配置文件



## Springboot 配置

### 配置文件

* application.properties
* application.yml

### YAML语法

#### 1. 基本语法

k: v 表示一对键值对

以空格的缩进来控制层级关系

#### 2. 值的写法

**字面量（数字，字符串，布尔）**

字符串默认不用加上单引号或者双引号

"": 双引号，不会转义字符串里面的特殊字符；特殊字符会作为本身想表示的意思。

​		“\n" 会输出换行

''：单引号，会转义特殊字符，特殊字符最终只是一个普通的字符串数据

​		'\n' 会输出 \n

**对象，Map**

```yaml
friend:
	lastName: wang
	age: 20
```

行内写法：

```yaml
friend: {lastNmae: zhangsan,age: 18};
```

**数组**

用- 值表示数组中的元素

```yaml
pets:
	- cat
	- dog
	- pig
```

行内写法：

```yaml
pets: [cat,dog,pig]
```



### 配置文件处理器

#### maven依赖：

```
				<!-- configuration processor 配置文件处理器 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
        </dependency>
```

#### 在application.yml 配置一个person类：

```
person:
  age: 22
  name: xiaowang
```

#### Java Person类：

```java
/**
 *  告诉springboot本类中的所有属性和配置文件中相关配置绑定
 *  只有这个组件在容器中，才能使用容器提供的ConfigurationProperties
 */
@Component
@ConfigurationProperties(prefix = "person")
public class Person {
    private int age;
    private String name;
    public void setAge(int age) {
        this.age = age;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}

```

#### SpringBoot 单元测试类：

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWordAppTest extends TestCase {

    @Autowired
    Person person;

    @Test
    public void contextLoads() {
        System.out.println(person);
    }

}
```

这样就可以获取到配置文件中配置的person类了

#### @Value 和 @ConfigurationProperties

|                      | @ConfigurationProperties | @Value          |
| -------------------- | ------------------------ | --------------- |
| 功能                 | 批量注入配置文件的属性   | 一个一个指定    |
| 松散绑定（松散语法） | 支持                     | 不支持          |
| SpEL                 | 不支持                   | 支持 "#{}" 计算 |
| JSR303数据校验       | 支持 (校验类似email格式) | 不支持          |
| 复杂类型封装         | 支持                     | 不支持          |

如果只要某项值，就用@Value；如果专门写了一个javaBean来映射配置文件，就直接使用ConfigurationProperties。

例如yml文件如下：

```yaml
person:
  age: 22
  last-Name: xiaowang
```

```java
private int age;
    private String lastName;
```

是仍然可以获取到lastName属性的，但是@Value则不行

### 配置文件注入值，数据校验

```java
@Component
@ConfigurationProperties(prefix = "person")
@Validated
public class Person {
    //@Value("${person.age}")
    private int age;
    @Email
    private String lastName;
    public void setAge(int age) {
        this.age = age;}
    public void setLastName(String lastName) {
        this.lastName = lastName;}
    public int getAge() {
        return age;}
    public String getLastName() {
        return lastName;}
    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + lastName + '\'' +
                '}';}
}

```







## Springboot 日志

## Springboot Web开发

## Springboot Docker

## Springboot 数据访问

## Springboot 启动配置原理

## Springboot 自定义starters
