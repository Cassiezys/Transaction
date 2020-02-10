## 大学生二手交易网站

## 资料
[Serving Web Content](https://spring.io/guides/gs/serving-web-content/)  
[Mybatis](https://mybatis.org/mybatis-3/zh/getting-started.html)  
[spring boot mybatis](https://mybatis.org/spring-boot-starter/)
[spring web mvc](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#spring-web)  
[Mybatis generator plugins](http://mybatis.org/generator/reference/plugins.html)  
[Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)
##工具
[bootstrap](https://v3.bootcss.com/):前端页面  
[developer tools 热部署](https://docs.spring.io/spring-boot/docs/2.2.3.RELEASE/reference/html/using-spring-boot.html#using-boot-devtools-restart):每次自动启动  
[Mybatis generator](http://mybatis.org/generator):生成各种版本的mybatis
[javaex](http://doc.javaex.cn/javaex/index.html)  
[Sprint lang3 工具包] 
[lombok]:pom+安装plugins
## Execution
```bash
- mybatis
mvn mybatis-generator:generate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
- 提示源码无法下载
mvn dependency:resolve -Dclassifier=sources
```  