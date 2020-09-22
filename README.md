

# Springboot

[本实例代码位于github：https://github.com/sunliancheng/springboot](https://github.com/sunliancheng/springboot)

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

#### 配置文件注入值，数据校验

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

### @PropertySource & @ImportResource

@PropertySource: 

```
@ConfigurationProperties(prefix = "person")
```

默认从全局配置文件中获取值, @PropertySource 可以从指定文件获取对象属性

```java
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
```

@ImportResource: 导入spring的配置文件

比如：自己写的spring配置文件中指定bean时，spring不会把它放入容器中。需要指定扫描配置文件，spring才会放入容器中。

在启动类上加入注解，即可：

```java
@ImportResource(locations = {"classpath:beans.xml"})
```

SpringBoot中推荐给容器添加组件方式：全注解方式

1. 配置类 ===== Spring配置文件

   

2. 使用@Bean给容器添加元素

   ```java
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
   ```

   单元测试类如下：

   ```java
   @RunWith(SpringRunner.class)
   @SpringBootTest
   public class HelloWordAppTest extends TestCase {
   
       @Autowired
       Person person;
   
       @Autowired
       ApplicationContext ioc;
   
       @Test
       public void contextLoads() {
           System.out.println(ioc.getBean("helloService"));
       }
   
   }
   ```

   

### 配置文件占位符

1. 随机数

   ```java
   ${random.value} ${random.int}
   ```

2. 占位符获取之前配置的值，如果没有用冒号指定默认值

```java
server.port=80
person.lastName=wdd${random.uuid}
person.age=${server.port}33
```

### Profile

##### 1. 多profile文件

主配置文件编写的时候，文件名可以是 application-{profile}.properties/yml

默认使用application.properties, 通过修改主配置文件可以指定配置文件启动

##### 2. yml 使用文档块

--- 可以切分文档块

```yam
server:
  port: 80
person:
  age: 22
  last-Name: xiaowang
spring:
  profiles:
    active: prod

---
server:
  port: 82
spring:
  profiles:
    active: dev
---
server:
  port: 84
spring:
  profiles:
    active: prod
```



##### 3. 激活指定profile

```
spring:
  profiles:
    active: prod
```

前提是classpath下已经有： application-prod.properties

使用命令行 --spring.profiles.active=dev 来指定启动

虚拟机参数启动 -Dspring.profiles.active=dev 

### 配置文件加载位置

Springboot会从以下位置扫描主配置文件：

按照优先级从高到低，高优先级会覆盖低优先级配置内容，这里的覆盖指的是四个位置的文件都加载进来，比如 file:./config 下配置了89端口，而 classpath:/ 下配置了使用 spring.profiles.active=dev ，这样就会从 application-dev.properties 中读取特定环境端口启动。**互补配置**

```
- file:./config
- file:./
- classpath:/config
- classpath:/
```

--spring.config.location 可以从指定位置的配置文件启动，通过使用命令行参数的形式来指定。会和其他配置文件互补，共同起作用。

```java
java -jar xxx.jar --spring.config.location=xxx
```

### 外部配置

1. 命令行打包

   多个参数空格隔开

2. 由jar包外向jar包内寻找

   优先加载带profile的

   再加载不带profile的

### 自动配置原理

1. springboot启动的时候，加载主配置类，开启了自动配置功能，@EnableAutoConfiguration

   将类路径下 META-INF/spring.factories 中所有的自动配置的值放入容器。只有自动配置类加入容器后，才能开始自动配置

   ```
   # Auto Configure
   org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
   org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
   org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
   org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
   org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration,\
   org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration,\
   org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration,\
   org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration,\
   org.springframework.boot.autoconfigure.context.LifecycleAutoConfiguration,\
   org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,\
   org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration,\
   org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration,\
   ```

2. 每个自动配置类进行自动配置

3. 以 **HttpEncodingAutoConfiguration** 为例

   ```java
   @Configuration(proxyBeanMethods = false)   //表示这是一个配置类
   @EnableConfigurationProperties(ServerProperties.class)  //启动指定类
   @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET) //@spring底层@Conditional注解，根据不同条件，如果满足指定条件，整个配置类才会生效； 判断当前是否为web应用
   @ConditionalOnClass(CharacterEncodingFilter.class) //判断当前项目有无这个类 SpringMVC中进行乱码解决的过滤器
   @ConditionalOnProperty(prefix = "server.servlet.encoding", value = "enabled", matchIfMissing = true)  //判断配置文件中是否存在某个配置  matchIfMissing 如果不存在也成立
   public class HttpEncodingAutoConfiguration {
   ```

   ```java
   @ConfigurationProperties(prefix = "server", ignoreUnknownFields = true) //从配置文件中获取指定的值和bean属性进行绑定
   public class ServerProperties {
   ```

   根据不同条件判断，如果生效后，给容器中添加组件。这些组件的属性是从对应的properties文件中获取的。

4. 把配置文件配置信息和配置类绑定在一起

5. **springboot启动加载大量自动配置类，**

   **看springboot这个自动配置类添加了哪些组件**

   **如果没有我们需要的配置，需要自己写个配置类**

​		xxxAutoConfiguration：自动配置类

​		xxxProperties: 封装对应的配置信息

### Conditional注解

#### 1. @Conditional派生注解

```java
@ConditionalOnJava 系统的java版本
@ConditionalOnBean 容器中存在指定Bean
@ConditionalOnMissingBean 容器中不存在指定Bean
@ConditionalOnClass 系统中有指定类
@ConditionalOnJndi JNDI存在指定项
@ConditionalOnProperty 系统中存在指定属性
```

​	自动配置类必须在一定条件下生效；如何知道哪些自动配置类生效？

​	配置文件中声明 debug=true，springboot会打印所有使用的自动配置类。	

```
Positive matches:
-----------------

   AopAutoConfiguration matched:
      - @ConditionalOnProperty (spring.aop.auto=true) matched (OnPropertyCondition)

   AopAutoConfiguration.ClassProxyingConfiguration matched:
      - @ConditionalOnMissingClass did not find unwanted class 'org.aspectj.weaver.Advice' (OnClassCondition)
      - @ConditionalOnProperty (spring.aop.proxy-target-class=true) matched (OnPropertyCondition)

```

## Springboot 日志

### 1. 日志框架

**市面上的日志框架**

JUL，JCL，Jboss-logging，log4j，log4j2，slf4j

| 日志门面 facade                                              | 日志实现                                                 |
| ------------------------------------------------------------ | -------------------------------------------------------- |
| ~~JCL（Jakarta Commons Logging)~~   SLF4j (Simple Logging Facade for java)   ~~jboss-logging~~ | ~~Log4j~~  ~~JUL(java.util.logging)~~   Log4j2   Logback |

日志门面： SLF4j  

日志实现： Logback

SPringboot 底层是spring 默认使用JCL； Springboot使用SLF4j 和 Logback

### 2. SLF4j 使用

1. 如何在系统中使用 SLF4j

   应该调用日志抽象层里面的方法，而不是直接调用日志的实现类

   ```java
   import org.slf4j.Logger;
   import org.slf4j.LoggerFactory;
   
   public class HelloWorld {
     public static void main(String[] args) {
       Logger logger = LoggerFactory.getLogger(HelloWorld.class);
       logger.info("Hello World");
     }
   }
   ```

   ![](http://www.slf4j.org/images/concrete-bindings.png)

   每一个日志的实现框架都有自己的配置文件，使用slf4j以后，配置文件还是要做成日志实现框架自己本身的配置文件。

2. 遗留问题

   统一日志记录，即使别的框架也用slf4j

   ![](http://www.slf4j.org/images/legacy.png)

   如何让系统中所有的日志统一到slf4j：

   * 排除系统中其他日志框架

   * 用中间包替换原有日志框架
   * 导入slf4j 其他的实现

### 3. springboot 日志关系

![image-20200922150543315](https://raw.githubusercontent.com/sunliancheng/image/master/image-20200922150543315.png?token=AJYIUN5BZOIJP73HNL6VJ4C7OLOZU)

​	springboot 底层也用 logback 和 slf4j 

​	springboot也把其他包导成了slf4j

​	如果要用其他框架，一定要移除这个框架的默认日志依赖，springboot也排除了commons-logging

​	

### 4. 日志使用

```java
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
```

可以通过修改配置文件来设置日志输出级别

```yaml
logging:
  level:
    com.xxx.springboot: trace
  file: springboot.log
```

```xml
logging.file.path=/
#logging.name=springboot.log
logging.file.name=springboot.log
logging.level.com.xxx.springboot=debug
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n
logging.pattern.console=%d{yyyy-MM-dd} === [%thread] === %-5level === %logger{50} === - %msg%n

```

通过修改日志配置文件 控制日志输出位置和格式

### 5. 日志使用高级功能

​	logback.xml 直接被日志框架识别

​	logback-spring.xml 能被spring识别，使用高级功能，比如spring-profile

### 6. 日志框架切换

* 排除系统中其他日志框架

* 用中间包替换原有日志框架

* 导入slf4j 其他的实现

  

## Springboot Web开发

### Springboot web简介

新建springboot，选用相应的模块，springboot默认会配置好各种场景







## Springboot Docker

## Springboot 数据访问

## Springboot 启动配置原理

## Springboot 自定义starters