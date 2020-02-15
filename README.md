## 大学生二手交易网站

## 资料
[Serving Web Content](https://spring.io/guides/gs/serving-web-content/)  
[Mybatis](https://mybatis.org/mybatis-3/zh/getting-started.html)  
[spring boot mybatis](https://mybatis.org/spring-boot-starter/)
[spring web mvc](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#spring-web)  
[Mybatis generator plugins](http://mybatis.org/generator/reference/plugins.html)：分页用的是它自带的插件实现的。  
[Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)  
[支付宝开放平台](https://docs.open.alipay.com/54/103419/)
##工具
[bootstrap](https://v3.bootcss.com/):前端页面  
[developer tools 热部署](https://docs.spring.io/spring-boot/docs/2.2.3.RELEASE/reference/html/using-spring-boot.html#using-boot-devtools-restart):每次自动启动  
[Mybatis generator](http://mybatis.org/generator):生成各种版本的mybatis  
[javaex](http://doc.javaex.cn/javaex/index.html)：商品展示的轮播效果  
[Sprint lang3 工具包]   
[moment.js](http://momentjs.cn/):javascript时间处理 
[lombok]:pom+安装plugins  
[沙箱支付](https://openhome.alipay.com/platform/appDaily.htm?tab=info)

## Execution
```bash
- mybatis
mvn mybatis-generator:generate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
- 提示源码无法下载
mvn dependency:resolve -Dclassifier=sources
```  