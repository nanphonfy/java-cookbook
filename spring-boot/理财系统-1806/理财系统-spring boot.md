### 理财APP的后台系统
>用户管理  
账户系统  
支付管理  
产品管理  
风险管理  
法规、资质

>快速（开发快、迭代快）、高效（高并发、响应快）、安全（加密、权限）

- 产品管理：
>销售端、管理端    
swagger junit hazelcast    
多节点部署 JSON-RPC    
HTTPS RSA签名 权限控制 节流限速     访问统计 TYK

### 产品系统
>构建工具-gradle  
快速开发-restful、spring boot、spring data jpa、自动化测试、swagger  
对账业务-对账业务介绍、对账文件的生成与解析、对账、平账、定时对账、JPA多数据源、JPA读写分离、源码修改  
高效优化-jsonrpc(源码修改、自动配置原理)、hazelcast、activemq、模块化封装
安全-HTTPS、RSA、TYK  
特色-多方面新技术、始于文档陷于源码目的、简单粗暴黑科技、为达目的不择手段、升级spring boot2.0  

- 收获：
>金融系统/相关业务知识  
编程思想及技术能力  
如何学习新技术  

`用户-登录、浏览->套壳公司-查询产品->销售端-查询产品->管理端
用户<-产品数据-套壳公司<-产品数据-销售端<-产品数据-管理端`

`用户-购买->套壳公司（逻辑处理）-下单->销售端-结果->套壳公司-结果->用户`

- 模块设计
>manager（功能实现、自动化测试、swagger） saller（jsonRpc、hazelcast、activemq、自动化测试mock、tyk（api网关、按流量收费等）、quartz）  
entity api  
util quartz swagger

- 为什么模块开发？
>高内聚，低耦合  
并行开发，提高开发效率  
轮子重复使用

- 如何划分？
>业务层次  
功能划分  
重复使用

- 相关概念
>构建工具  
项目  
模块   
工程  
应用  
gradle spring boot

- 管理端
>产品表
>>编号、名称、收益率、锁定期、状态、起投金额、投资步长、备注、创建时间、创建者、更新时间、更新者

>订单
>>订单编号、渠道编号（第三方套壳公司）、产品编号、用户编号、外部订单编号（套壳公司）、类型、状态、金额、备注、创建时间、更新时间

```SQL
CREATE DATABASE`manager`;

USE `manager`;

CREATE TABLE `product` (
  `id` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '产品编号',
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '产品名称',
  `threshold_amount` decimal(15,3) NOT NULL COMMENT '起投金额',
  `step_amount` decimal(15,3) NOT NULL COMMENT '投资步长',
  `lock_term` smallint(6) NOT NULL COMMENT '锁定期',
  `reward_rate` decimal(5,3) NOT NULL COMMENT '收益率,0-100 百分比值',
  `status` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态,AUDINTING:审核中,IN_SELL:销售中,LOCKED:暂停销售,FINISHED:已结束',
  `memo` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建者',
  `update_at` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


mysql> SELECT t.TABLE_NAME,t.TABLE_COMMENT,c.COLUMN_NAME,c.COLUMN_TYPE,c.IS_NULLABLE,c.COLUMN_KEY, c.COLUMN_DEFAULT,c.COLUMN_COMMENT
		FROM information_schema.TABLES t,INFORMATION_SCHEMA.Columns c 
		WHERE c.TABLE_NAME=t.TABLE_NAME 
		AND t.`TABLE_SCHEMA`='manager';
+------------+---------------+------------------+---------------+-------------+------------+----------------+----------------------------------------------------------------------+
| TABLE_NAME | TABLE_COMMENT | COLUMN_NAME      | COLUMN_TYPE   | IS_NULLABLE | COLUMN_KEY | COLUMN_DEFAULT | COLUMN_COMMENT                                                       |
+------------+---------------+------------------+---------------+-------------+------------+----------------+----------------------------------------------------------------------+
| product    |               | id               | varchar(50)   | NO          | PRI        | NULL           | 产品编号                                                             |
| product    |               | name             | varchar(50)   | NO          |            | NULL           | 产品名称                                                             |
| product    |               | threshold_amount | decimal(15,3) | NO          |            | NULL           | 起投金额                                                             |
| product    |               | step_amount      | decimal(15,3) | NO          |            | NULL           | 投资步长                                                             |
| product    |               | lock_term        | smallint(6)   | NO          |            | NULL           | 锁定期                                                               |
| product    |               | reward_rate      | decimal(5,3)  | NO          |            | NULL           | 收益率,0-100 百分比值                                                |
| product    |               | status           | varchar(20)   | NO          |            | NULL           | 状态,AUDINTING:审核中,IN_SELL:销售中,LOCKED:暂停销售,FINISHED:已结束 |
| product    |               | memo             | varchar(200)  | YES         |            | NULL           | 备注                                                                 |
| product    |               | create_at        | datetime      | YES         |            | NULL           | 创建时间                                                             |
| product    |               | create_user      | varchar(20)   | YES         |            | NULL           | 创建者                                                               |
| product    |               | update_at        | datetime      | YES         |            | NULL           | 更新时间                                                             |
| product    |               | update_user      | varchar(20)   | YES         |            | NULL           | 更新者                                                               |
+------------+---------------+------------------+---------------+-------------+------------+----------------+----------------------------------------------------------------------+

--mysql使用sql语句查询数据库所有表注释已经表字段注释
https://www.cnblogs.com/007sx/p/7093429.html

--datetime表示范围更广，timestamp会转换为用户对应的时区（跨时区）

CREATE DATABASE`seller`;

USE `seller`;

CREATE TABLE `order_t` (
  `order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '订单编号',
  `chan_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '渠道编号',
  `product_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '产品编号',
  `chan_user_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '渠道用户编号',
  `order_type` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '类型,APPLY:申购,REDEEM:赎回',
  `order_status` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态,INIT:初始化,PROCESS:处理中,SUCESS:成功,FAIL:失败',
  `outer_order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '外部订单编号',
  `amount` decimal(15,3) NOT NULL COMMENT '金额',
  `memo` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  `update_at` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

mysql> SELECT t.TABLE_NAME,t.TABLE_COMMENT,c.COLUMN_NAME,c.COLUMN_TYPE,c.IS_NULLABLE,c.COLUMN_KEY, c.COLUMN_DEFAULT,c.COLUMN_COMMENT
		FROM information_schema.TABLES t,INFORMATION_SCHEMA.Columns c 
		WHERE c.TABLE_NAME=t.TABLE_NAME 
		AND t.`TABLE_SCHEMA`='seller';
+------------+---------------+----------------+---------------+-------------+------------+----------------+-------------------------------------------------------+
| TABLE_NAME | TABLE_COMMENT | COLUMN_NAME    | COLUMN_TYPE   | IS_NULLABLE | COLUMN_KEY | COLUMN_DEFAULT | COLUMN_COMMENT                                        |
+------------+---------------+----------------+---------------+-------------+------------+----------------+-------------------------------------------------------+
| order_t    |               | order_id       | varchar(50)   | NO          | PRI        | NULL           | 订单编号                                              |
| order_t    |               | chan_id        | varchar(50)   | NO          |            | NULL           | 渠道编号                                              |
| order_t    |               | product_id     | varchar(50)   | NO          |            | NULL           | 产品编号                                              |
| order_t    |               | chan_user_id   | varchar(50)   | NO          |            | NULL           | 渠道用户编号                                          |
| order_t    |               | order_type     | varchar(50)   | NO          |            | NULL           | 类型,APPLY:申购,REDEEM:赎回                           |
| order_t    |               | order_status   | varchar(50)   | NO          |            | NULL           | 状态,INIT:初始化,PROCESS:处理中,SUCESS:成功,FAIL:失败 |
| order_t    |               | outer_order_id | varchar(50)   | NO          |            | NULL           | 外部订单编号                                          |
| order_t    |               | amount         | decimal(15,3) | NO          |            | NULL           | 金额                                                  |
| order_t    |               | memo           | varchar(200)  | YES         |            | NULL           | 备注                                                  |
| order_t    |               | create_at      | datetime      | YES         |            | NULL           | 创建时间                                              |
| order_t    |               | update_at      | datetime      | YES         |            | NULL           | 更新时间                                              |
+------------+---------------+----------------+---------------+-------------+------------+----------------+-------------------------------------------------------+
```

- 管理端
>>添加产品-POST-/products  
查询单个产品-GET-/products/{id}  
条件查询产品-GET-/products

