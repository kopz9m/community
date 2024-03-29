## 麻酱社区

## 在线演示地址
[麻酱社区](http://81.68.148.241)

## 功能列表  
开源论坛、问答系统;
GitHub、邮箱登录功能；
提问、回复、通知等功能

## 本地运行手册
1. 安装必备工具  
JDK，Maven
2. 克隆代码到本地
```sh
git clone https://github.com/kopz9m/community.git
````
3. 运行DB目录下数据库脚本，创建数据库

```
4. 运行打包命令，生成可执行 jar 文件
```sh
mvn package
```
4. 运行项目  
```sh
java -jar target/community-0.0.1-SNAPSHOT.jar
```
线上部署需要新建配置文件(production.properties)，同时运行命令修改如下
```sh
java -jar -Dspring.profiles.active=production target/community-0.0.1-SNAPSHOT.jar
```
5. 访问项目
```
http://localhost:8887
```

## 其他
1. 请修改配置文件 application.properties.example 并重命名为 application.properties

2. 生成 Model 等 MyBatis 配置文件的命令
```
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```


## 技术栈
|  技术   |  链接   |
| --- | --- |
|  Spring Boot   |  http://projects.spring.io/spring-boot/#quick-start   |
|   MyBatis  |  https://mybatis.org/mybatis-3/zh/index.html   |
|   MyBatis Generator  |  http://mybatis.org/generator/   |
|   Flyway  |   https://flywaydb.org/getstarted/firststeps/maven  |
|Lombok| https://www.projectlombok.org |
|Bootstrap|https://v3.bootcss.com/getting-started/|
|Github OAuth|https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/|
|Bootstrap|https://v3.bootcss.com/getting-started/|

## 扩展资料
[Spring 文档](https://spring.io/guides)    
[Spring Web](https://spring.io/guides/gs/serving-web-content/)   
[es](https://elasticsearch.cn/explore)    
[Github deploy key](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)    
[Bootstrap](https://v3.bootcss.com/getting-started/)    
[Github OAuth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)    
[Spring](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#boot-features-embedded-database-support)    
[Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#setting-attribute-values)    
[Spring Dev Tool](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#using-boot-devtools)  
[Spring MVC](https://docs.spring.io/spring/docs/5.0.3.RELEASE/spring-framework-reference/web.html#mvc-handlermapping-interceptor)  
[Markdown 插件](http://editor.md.ipandao.com/)   
[Git](https://git-scm.com/download)   
[Flyway](https://flywaydb.org/getstarted/firststeps/maven)  
[Lombok](https://www.projectlombok.org)    
[Postman](https://chrome.google.com/webstore/detail/coohjcphdfgbiolnekdpbcijmhambjff)
[MultipartFile](https://www.bezkoder.com/spring-boot-upload-multiple-files/)
