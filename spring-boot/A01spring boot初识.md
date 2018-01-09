- 配置spring boot骨架的网址
http://start.spring.io/

## 1. initializr-start

```
java -version
gradle -v
cd initializr-start
gradle build
java -jar build/libs/initializr-start-0.0.1-SNAPSHOT.jar
```
>- gradle安装配置
https://jingyan.baidu.com/article/4d58d541167bc69dd4e9c009.html


### 1.1 构建脚本：build.gradle
```
// buildscript 代码块中脚本优先执行
buildscript {

	// ext 用于定义动态属
	ext {
		springBootVersion = '1.4.3.RELEASE'
	}
	
	// 使用了 Maven 的中央仓库（你也可以指定其他仓库）
	repositories {
		mavenCentral()
	}
	
	// 依赖关系
	dependencies {
		// classpath 声明说明了在执行其余的脚本时，ClassLoader 可以使用这些依赖项
		classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
	}
}

// 使用插件
apply plugin: 'java'
apply plugin: 'eclipse'
apply plugin: 'org.springframework.boot'

// 打包的类型为 jar，并指定了生成的打包的文件名称和版本
jar {
	baseName = 'initializr-start'
	version = '0.0.1-SNAPSHOT'
}

// 指定编译 .java 文件的 JDK 版本
sourceCompatibility = 1.8

// 使用了 Maven 的中央仓库
repositories {
	mavenCentral()
}

// 依赖关系
dependencies {
	// 该依赖对于编译发行是必须的
	compile('org.springframework.boot:spring-boot-starter-web')
	// 该依赖对于编译测试是必须的，默认包含编译产品依赖和编译时依
	testCompile('org.springframework.boot:spring-boot-starter-test')
}
```
>-Gradle 3 User Guide 中文翻译《Gradle 3 用户指南》
https://github.com/waylau/Gradle-3-User-Guide

- 自定义存储库，加速构建，eg.添加阿里云的中央仓库
```
// 默认使用了 Maven的中央仓库。这里改用自定义的镜像库
repositories {
	//mavenCentral()
	maven {
		url 'http://maven.aliyun.com/nexus/content/groups/public/'
	}
}
```

- 三种运行方式：
>①java -jar；  
②java application；  
③spring boot gradle plugin插件（gradle bootRun）

- 对controller进行单元测试
```
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;
	
    @Test
    public void testHello() throws Exception {
    	mockMvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Hello World! Welcome to visit waylau.com!")));
    }
}
```

## 2. 理解Thymeleaf
>- java模板引擎。能够处理HTML、XML、JavaScript、CSS、纯文本。类似JSP、freemarker
>- 自然模板。原型即页面
>- 语法优雅易懂。OGNL、SpringEL
>- 遵从web标准，支持HTML5

- 论技术选型的重要性，JSP还是Thymeleaf？
https://www.imooc.com/article/20304

- Thymeleay与spring boot集成
```
// 添加 Thymeleaf 的依赖
compile('org.springframework.boot:spring-boot-starter-thymeleaf')
```

## 3. Spring Data JPA 
>- JPA:用于管理java EE和java SE环境中的持久化，以及对象/关系映射的java api。
>- 实现：hibernate、eclipseLink、Apache openJPA

- JPA核心概念
>- 实体：
>>①实体表示数据库表；  
②每个实体实例->表中的行；  
③必须用javax.persistence.Entity注解；  
④必须有一个public或protected无参构造；  
⑤实体实例被当做值以分离对象传递时（eg.会话bean的远程业务接口），必须实现Serializable接口；  
⑥唯一的对象标识符。
>- 关系：
>>一对一、一对多、多对一、多对多
>- EntityManager接口
>>①定义持久性上下文交互方法；  
②增删持久实体实例，通过主键查找；  
③允许在实体上运行查询。

- Spring Data JPA 
>①是更大的spring Data家族的一部分；  
②对基于JPA的数据访问层的增强支持；  
③更容易构建基于使用spring数据访问技术栈的应用程序；

- 常用接口
>CrudRepository  
PagingAndSortingRepository  
根据方法名创建查询

- Spring Boot 集成,集成过程测试
```
配置环境
// 自定义  Hibernate 的版本
ext['hibernate.version'] = '5.2.8.Final'

// 添加 Spring Data JPA 的依赖
compile('org.springframework.boot:spring-boot-starter-data-jpa')
	
// 添加 MySQL连接驱动 的依赖
compile('mysql:mysql-connector-java:6.0.5')
```

- 数据持久化
修改application.properties
```
# DataSource 
spring.datasource.url=jdbc:mysql://localhost/blog?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC 
spring.datasource.username=root
spring.datasource.password=ZNF123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA
spring.jpa.show-sql = true
spring.jpa.hibernate.ddl-auto=create-drop
```
后台编码

## 4. ElasticSearch
>- 高度可扩展的开源全文搜索和分析引擎；
>- 快速地、近实时地对大数据进行存储、搜索和分析；
>- 用来支撑有复杂的数据搜索需求的企业级应用。

- 特点：
>分布式、高可用、多类型、多API、面向文档、异步写入、近实时、基于Lucene、Apache协议

- 核心概念：
>近实时（轻微延时，1s左右，每隔n秒做刷新）  
集群（多个节点集合，保存应用所有数据，提供集成所有节点索引和搜索功能）  
节点（名称一般用UUID）  
索引（相似文档的结合）  
类型（根据文档公共属性划分）  
文档（进行索引的基本单位，使用json格式）  
分片（允许索引分片，建立多个副本，水平分割缩放，并行，提高性能和吞吐量）  
副本（故障不可避免，要分布到不同节点，也可以提升吞吐量和搜索量）  

- Elasticsearch 实-文档、资源库
```
// 添加  Spring Data Elasticsearch 的依赖
compile('org.springframework.boot:spring-boot-starter-data-elasticsearch')

// 添加  JNA 的依赖
compile('net.java.dev.jna:jna:4.3.0')
```

- 全文搜索概述
>- 数据结构
>>结构化：固定格式或有限长度，eg.数据库、元数据等；  
非结构化：不定长或无固定格式，eg.邮件、word文档。

- 非结构化数据检索：
>顺序扫描法（eg.Linux扫描文件内容），全文搜索（将非结构转为结构化数据，再建立索引）

- 全文搜索概念：  
>将文件中所有文本与搜索项匹配的文字资料检索的方法

- 全文搜索实现原理：
>建文本库->建立索引->执行搜索->过滤结果（eg.分页）

- 基于java的开源实现
> Lucene（发动机）、ElasticSearch（有它自己的管理系统，只支持json，提供restful）、Solr（利用zookeeper，实时性更低，但支持的数据更多）

实战：
```
# 内嵌 Elasticsearch 实例。默认存储位置是工作目录的 elastic 目录
spring.data.elasticsearch.properties.path.home=target/elastic
# 设置连接超时时间
spring.data.elasticsearch.properties.transport.tcp.connect_timeout=120s
```

**启动：** `elasticsearch-6.1.1\bin\elasticsearch.bat`  
**数据存储：** `elasticsearch-6.1.1\data`

## 5. 架构  
三层架构 VS. MVC  
>三层架构不等于MVC  
请求&响应->表示层（控制层&视图）->业务层（模型）->数据访问层（DAO）->数据库

>博客系统->关系型数据库&elasticsearch
>文件管理系统->MongoDB
>>两个系统通过restful api进行交互

## 6. bootstrap
>- 基于HTML、CSS、JavaScript
>- 响应式布局
>- 移动设备优先

- 移动设备优先策略
>基础的CSS是移动优先
>媒体查询（针对平板，台式机）
>渐进增强（随屏幕调节元素）

>**响应式：** viewport尺寸，系统自动分为最多12列

- 需求分析  
核心功能：
>博客系统（用户管理、安全设置、博客管理、评论管理、点赞管理、分类管理、标签管理、首页搜索）

- 首页搜索  
>全文检索、最新、最热、标签、用户、文章

## 7. spring security
>核心领域概念
>- 认证：认证是建立主体（可在程序中执行操作的用户、设备或其他系统）的过程；
>- 授权：访问控制，决定是否允许其在程序中执行操作

- 身份验证技术
>HTTP BASIC、HTTP Digest、HTTP X.509、LDAP、基于表单认证、OpenID、单点登录、Remember-Me、匿名身份验证、Run-as、JAAS、java ee容器认证。

- 模块
>Core、Remoting、Web、Config、LDAP、ACL、CAS、OpenID、Test

- spring security与spring boot集成
```
// 添加  Spring Security 依赖
compile('org.springframework.boot:spring-boot-starter-security')
```
基于角色的权限管理

- 角色：  
>①代表一系列行为或责任的实体；  
②限定能做什么、不能做什么；  
③用户账号往往与角色关联。

- RBAC:
>基于角色的访问控制（role-based access control）、隐式访问控制、显示访问控制

- 解决方案:
>Apache Shiro、Spring security

- 角色管理的需求
角色授权、权限设置
```
// 添加  Spring Security 依赖
compile('org.springframework.boot:spring-boot-starter-security')
```

- 权限管理
角色授权、权限设置

功能：
>建立角色与资源的关系、CSRF问题的解决、启用方法级别的安全设置、使用BCrypt加密密码、用户登录、记住我
>>CSRF（Cross-site request forgery）:跨站请求伪造

>像put、delete操作都得在ajax处理CSRF的token，在发送请求前，将其填入。

- 博客管理  
（发表、编辑、删除、分类、设置标签、上传图片、模糊查询、、最新最热排序、阅读量统计）

- 评论管理  
发表、删除评论、评论量统计

- 点赞管理  
点赞、取消、统计

- 分类管理（个人）  
创建、删除、查询

- 标签管理（公有）  
增删查

## 8 api设计
### 8.1 用户管理

++f：false、t：true++
- @PreAuthorize("hasAuthority('ROLE_ADMIN')")  
>- 查看所有用户：GET /users

参数 | 是否必须|默认值
---|---|---
async| f| -
pageIndex| f| 0
pageSize| f| 10
name| f| ""
>- 创建和修改用户：POST /users  

参数 | 是否必须|默认值|备注
---|---|---|---
User对象|t|-|含用户所有属性
authorityId|t|-|后台根据它得到角色权限
>- 删除用户：DELETE /users/{id}

参数 | 是否必须|默认值|备注
---|---|---|---
id|t|-|用户id（@PathVariable）

## 8.2 个人用户
@PreAuthorize("authentication.name.equals(#username)") 
>-查看个人主页：GET u/{username}/profile

参数 |备注
---|---
username| @PathVariable

@PreAuthorize("authentication.name.equals(#username)") 
>- 保存个人设置：POST u/{username}/profile

参数 |备注
---|---
username| @PathVariable

@PreAuthorize("authentication.name.equals(#username)") 
>- 保存头像：POST u/{username}/avatar

参数 |备注
---|---
username | @PathVariable
User |@RequestBody

>- 查看用户博客（排序）：GET u/{username}/blogs

参数 | 是否必须|默认值|备注
---|---|---|---
username| t |- |@PathVariable
order| f| new| @RequestParam
catalog| f| - |@RequestParam
keyword| f| "" |@RequestParam
async| f| - |@RequestParam
pageIndex| f| 0 |@RequestParam
pageSize| t| 10 |@RequestParam

>- 获取博客展示界面:GET u/{username}/blogs/{id}

参数 |备注
---|---
username | @PathVariable
id| @PathVariable

@PreAuthorize("authentication.name.equals(#username)") 
>- 删除博客： u/{username}/blogs/{id}

参数 |备注
---|---
username | @PathVariable
id| @PathVariable

@PreAuthorize("authentication.name.equals(#username)") 
>- 编辑博客： u/{username}/blogs/edit/{id}

参数 |备注
---|---
username | @PathVariable
id| @PathVariable

@PreAuthorize("authentication.name.equals(#username)") 
>- 保存博客： u/{username}/blogs/edit

参数 |备注
---|---
username| @PathVariable
Blog| @RequestBody

@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')") 
>- 发表点赞：POST votes

参数 |备注
---|---
blogId|-

@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
>- 删除点赞：DELETE votes/{id}

参数 |备注
---|---
blogId|-
id| @PathVariable


全局：
>- 首页博客:GET /blogs
参数 | 是否必须|默认值
---|---|---
order| f| "new"
keyword| f| ""
async| f| -
pageIndex| f| 0
pageSize| f| 10

开源项目示例：
- spring boot要如何学习？
https://www.zhihu.com/question/53729800
https://gitee.com/shuzheng/zheng
https://gitee.com/YYDeament/88ybg