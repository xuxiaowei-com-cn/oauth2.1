# OAuth2.1

## OAuth 2.1 授权框架

- 互联网草稿是最长有效期为六个月时间的草稿文件，并且可能随时被其他文件更新、替换或废止。

- [草案 v2-1-00](https://datatracker.ietf.org/doc/html/draft-ietf-oauth-v2-1-00)
    - 开始时间：2020年7月30日
    - 过期时间：2021年1月31日
- [草案 v2-1-01](https://datatracker.ietf.org/doc/html/draft-ietf-oauth-v2-1-01)
    - 开始时间：2021年2月1日
    - 过期时间：2021年8月5日
- [草案 v2-1-02](https://datatracker.ietf.org/doc/html/draft-ietf-oauth-v2-1-02)
    - 开始时间：2021年3月15日
    - 过期时间：2021年9月16日
- [草案 v2-1-03](https://datatracker.ietf.org/doc/html/draft-ietf-oauth-v2-1-03)
    - 开始时间：2021年9月8日
    - 过期时间：2022年3月12日
- [草案 v2-1-04](https://datatracker.ietf.org/doc/html/draft-ietf-oauth-v2-1-04)
    - 开始时间：2021年10月5日
    - 过期时间：2022年4月8日
- [草案 v2-1-05](https://datatracker.ietf.org/doc/html/draft-ietf-oauth-v2-1-05)
    - 开始时间：2022年3月7日
    - 过期时间：2022年9月8日

## [GitHub](https://github.com/spring-projects/spring-authorization-server)

## [mvnrepository.com](https://mvnrepository.com/artifact/org.springframework.security/spring-security-oauth2-authorization-server)

## Spring 文档

- [Spring 授权服务器参考](https://docs.spring.io/spring-authorization-server/docs/current/reference/html/index.html)
    - [概述](https://docs.spring.io/spring-authorization-server/docs/current/reference/html/overview.html)
    - [获得帮助](https://docs.spring.io/spring-authorization-server/docs/current/reference/html/getting-help.html)
    - [配置模型](https://docs.spring.io/spring-authorization-server/docs/current/reference/html/configuration-model.html)
    - [核心模型/组件](https://docs.spring.io/spring-authorization-server/docs/current/reference/html/core-model-components.html)
    - [操作指南](https://docs.spring.io/spring-authorization-server/docs/current/reference/html/how-to.html)

## 启动（体验）步骤

- 启动 authorization-server、client
- [访问](http://127.0.0.1:1301/oauth2/authorize?client_id=xuxiaowei_client_id&redirect_uri=http://127.0.0.1:1401/code&response_type=code&scope=snsapi_base&state=beff3dfc-bad8-40db-b25f-e5459e3d6ad7)
- 用户名：user
- 密码：password

## 分支描述

- main
    - 最基础的功能
- key-store
    - 使用秘钥文件
- RSA-key
    - 使用 RSA 秘钥对
- mysql
    - 使用 MySQL 数据库
- mysql_jdbc_store
    - MySQL 授权码、授权Token、刷新Token、授权 持久化
- WebClient
    - 使用 WebClient、webflux、oauth2-client
- resource_withPublicKey
    - 使用 withPublicKey 的 resource
- consentPage
    - 自定义授权页面
