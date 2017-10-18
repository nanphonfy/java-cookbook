>spring开启某些特性(eg.事务管理和Spring MVC)、启用第三方库(eg.基于Thymeleaf的Web视图)、启用配置Servlet和过滤器（eg.Spring的 DispatcherServlet->web.xml或Servlet初始化代码里),都需要XML或Java显式配置。

>组件扫描减少了配置量，但还是需要不少配置。写配置挤占了写程序逻辑的时间。此外，项目管理依赖要知道这些库的哪个版本和其他库不会有冲突，一旦选错了依赖版本，会造成不兼容问题。

### 1.1 重新认识spring
>用spring开发简单的web应用，需要：
①Maven或Gradle，SpringMVC和Servlet API；②web.xml（声明了Spring的DispatcherServlet）；③启用了Spring MVC的Spring配置；④控制器类；⑤Tomcat。
>>只有控制器与我们的应用有关，剩下的都是Spring开发Web程序必需的通用样板。
```
@RestController
class HelloController {
  @RequestMapping("/")
  def hello() {
    "Hello World"
  }
}
```
>groovy没配置、web.xml、构建说明、应用服务器，就构成了整个应用程序。spring boot的简洁与groovy有异曲同工之妙，会搞定各种后勤。spring boot CLI(Command Line Interface)可运行未编译代码，eg.spring run HelloController.groovy。

### 1.2 精要
>四个核心：
>>①自动提供配置；②起步依赖，它能帮忙引库；③命令行界面，无需构建；④Actuator。

- 自动配置  
>spring需要样板配置，而spring boot在classpath里发现jdbcTemplate会自己配置一个bean。自动配置设计JPA、Spring MVC和模板、安全等。

- 起步依赖
>boot利用传递依赖解析（maven+gradle）把常用库聚合在一起（版本都经过测试，不会不兼容），组成几个为特定功能而定制的依赖。
构建web应用程序，就加入org.springframework.boot:spring-boot-starter-web的起步依赖等。

- 命令行界面
>spring boot CLI（非必要组成，简化开发，但不太常规）利用起步依赖和自动配置，能检测使用了哪些类，要向classpath中添加哪些起步依赖。

- Actuator
>提供在运行时检视程序内部情况的能力：上下文配置的bean、自动配置的决策、环境变量,系统属性,配置属性和命令行参数、线程状态、最近的HTTP请求跟踪、内存用量，垃圾回收，web请求+数据源用量。
通过web端点和shell界面向外界提供信息。

### 1.3 澄清
>spring boot不是应用服务器（是内嵌的servlet容器提供），没有实现JPA或JMS规范（是配置了某JPA实现），没引入代码生成（用了spring4的条件化配置+传递依赖解析（maven+gradle））。本质上，spring boot就是spring。

代码：https://www.manning.com/books/spring-boot-in-action

