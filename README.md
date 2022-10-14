**项目说明** 
- lotus-cloud是基于spring-cloud-alibaba 微服务架构，针对业务系统提供扩展，快速开发。
- 拉取后编译成jar，继承该项目即可使用。可放置私有maven库。
<br> 
<br>

 

**具有如下特点** 

- 开发人员只需关注业务实现。
- 公共工具类、报错收集、业务请求返回结果集。
- 数据库多配置源支持、表字段6要素。
- 统一重复代码、减少冗余代码、提升可读性。
- 完善的代码生成机制，可在线生成entity、xml、dao、service、sql代码，减少70%以上的开发任务(lotus-generator)
- 多租户支持
- 自动校验认证、权限安全、用户session(搭配网关lotus-gateway实现多应用单点登录)
- 日志一体化收集，消息发送。
- spring-cloud可扩展应用spi实现远程接口调用
- 可扩展swagger文档支持，方便编写API接口文档
<br> 

**项目结构** 
```
lotus-cloud
│
├─common 公共模块(工具类、结果集)
│ 
├─redis 缓存数据库(操作缓存工具)
│ 
├─datasources 数据源(MybatisPlus、多数据源、事务切面) ─common
│ 
├─web web服务（异常统一处理、id加密） ─datasources
│ 
├─framework 整体架构（session会话、基础6要素处理） ─web
│
├─logger 日志（日志服务,注解方式保存日志,推送到mq.）─framework
│
├─auth 认证（用户信息jwt生成token保存到redis,用户登录、认证模块调用）─common、redis
│  
├─security 授权安全（校验请求接口是否有权限访问,需要存储用户权限到缓存中）─暂未
│  
├─spi 远程调用接口（生成spi给其他模块应用调用，使用注册中心和调本地接口一样简单）─common

```
<br> 



**技术选型：**
- 核心框架：Spring 5.3.16 
  - Spring Boot 2.6.4 
  - Spring Cloud Alibaba 2021.1
- 持久层框架：MyBatis 3.5.3
- 工具类：Hutool:5.7.16
- 注册中心：Nacos 2.0.3
- 日志管理：SLF4J 
<br> 


 **备注**
- 如有疑问请联系changji_z@163.com
- 

<br> 


