# wordict
프로젝트에 필요한 단어를 등록하고 관리할 수 있는 웹 어플리케이션

## 1. Tomcat Maven Plugin 설정
웹 어플리케이션 개발을 위해 maven기반의 프로젝트를 생성합니다.
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>kr.pe.jady</groupId>
  <artifactId>wordict</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>war</packaging>
  <name>wordict</name>
  <description>프로젝트에 필요한 단어를 등록하고 관리할 수 있는 웹 어플리케이션</description>
  ...
</project>
```

pom.xml의 설정에서 사용될 프로퍼티를 설정합니다.
```xml
<project>
  ...
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.build.compile>1.8</project.build.compile>
  </properties>
  ...
</project>
```

JSP를 UI 템플릿으로 사용하기 위하여 의존성을 추가합니다.
```xml
<project>
  ...
  <dependencies>
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>3.1.0</version>
      <scope>provided</scope>
    </dependency>
    <dependency>
      <groupId>javax.servlet.jsp</groupId>
      <artifactId>jsp-api</artifactId>
      <version>2.2</version>
      <scope>provided</scope>
    </dependency>
    ...
  </dependencies>
  ...
</project>
```

빌드를 위해 Maven Compiler Plugin을 설정합니다.
```xml
<project>
  ...
  <build>
    <finalName>wordict</finalName>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.3</version>
        <configuration>
          <source>${project.build.compile}</source>
          <target>${project.build.compile}</target>
          <encoding>${project.build.sourceEncoding}</encoding>
        </configuration>
      </plugin>
      ...
    </plugins>
    ...
  </build>
  ...
</project>
```

웹 어플리케이션이므로 war패키징을 위해 Maven War Plugin을 설정합니다.
```xml
<project>
  ...
  <build>
    ...
    <plugins>
      ...
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-war-plugin</artifactId>
        <version>2.6</version>
        <configuration>
          <failOnMissingWebXml>false</failOnMissingWebXml>
        </configuration>
      </plugin>
      ...
    </plugins>
    ...
  </build>
  ...
</project>
```

웹 어플리케이션을 구동하여 확인해 볼 수 있도록 Tomcat Maven Plugin을 설정합니다.
```xml
<project>
  ...
  <build>
    ...
    <plugins>
      ...
      <plugin>
        <groupId>org.apache.tomcat.maven</groupId>
        <artifactId>tomcat7-maven-plugin</artifactId>
        <version>2.2</version>
        <configuration>
          <port>8088</port>
          <uriEncoding>${project.build.sourceEncoding}</uriEncoding>
          <path>/${project.build.finalName}</path>
        </configuration>
      </plugin>
      ...
    </plugins>
    ...
  </build>
  ...
</project>
```

다음 명령을 통해 웹 어플리케이션을 구동할수 있습니다.
```sh
mvn tomcat7:run
```

#### References
+ [Tomcat Maven Plugin 설정](https://tomcat.apache.org/maven-plugin-trunk/tomcat7-maven-plugin/)

## 2. Spring MVC 설정
Spring MVC 설정을 위해 의존성을 추가합니다.
```xml
<project>
  ...
  <dependencies>
    ...
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>${org.springframework.version}</version>
    </dependency>
    ...
  </dependencies>
  ...
</project>
```

Spring MVC 설정을 합니다.
```java
package kr.pe.jady.wordict.config.spring.web;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
    
}
```

```java
package kr.pe.jady.wordict.config.spring.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"kr.pe.jady.wordict"})
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp().prefix("WEB-INF/views").suffix(".jsp");
    }
    
}
```

테스트를 위해 의존성을 추가합니다.
```xml
<project>
  ...
  <dependencies>
    ...
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-test</artifactId>
      <version>${org.springframework.version}</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.mockito</groupId>
      <artifactId>mockito-core</artifactId>
      <version>1.10.19</version>
      <scope>test</scope>
    </dependency>
    ...
  </dependencies>
  ...
</project>
```

간단한 컨트롤러 테스트 코드를 작성합니다.
```java
package kr.pe.jady.wordict.common.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import kr.pe.jady.wordict.config.spring.web.WebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
public class HomeControllerTest {
    
    private MockMvc mockMvc;
    @InjectMocks
    private HomeController controller;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testMoveToHome() throws Exception {
        mockMvc.perform(get("/home.do"))
            .andExpect(status().isOk())
            .andExpect(view().name("/home"));
    }

}
```

컨트롤러를 작성합니다.
```java
package kr.pe.jady.wordict.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
    
    @RequestMapping(value = "/home.do", method = RequestMethod.GET)
    public String moveToHome() {
        return "/home";
    }

}
```

#### References
+ [Spring Framework Reference Documentation - 14.6. Spring MVC Test Framework](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#spring-mvc-test-framework)
+ [Spring Framework Reference Documentation - 21.15. Code-based Servlet container initialization](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#mvc-container-config)
+ [Spring Framework Reference Documentation - 21.16. Configuring Spring MVC](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#mvc-config)

## 3. Bootstrap 설정
부트스트랩을 [http://getbootstrap.com/](http://getbootstrap.com/)에서 내려받아 프로젝트에 추가합니다.

테스트 화면에 부트스트랩 기본 설정을 추가합니다.
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">
<title>Title of the document</title>
<!-- Bootstrap core CSS -->
<link href="${pageContext.request.contextPath}/external/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<link href="${pageContext.request.contextPath}/external/etc/css/ie10-viewport-bug-workaround.css" rel="stylesheet">
<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath}/external/etc/css/dashboard.css" rel="stylesheet">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->
</head>
<body>
...
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${pageContext.request.contextPath}/external/jquery/jquery-1.12.0.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${pageContext.request.contextPath}/external/bootstrap/js/bootstrap.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="${pageContext.request.contextPath}/external/etc/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
```

정적 자원에 대한 관리를 위해 Spring MVC 설정에 리소스 핸들러 설정을 추가합니다.
```java
package kr.pe.jady.wordict.config.spring.web;

...

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"kr.pe.jady.wordict"})
public class WebConfig extends WebMvcConfigurerAdapter {

    ...
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/external/**").addResourceLocations("/assets/external/");
        registry.addResourceHandler("/css/**").addResourceLocations("/assets/css/");
        registry.addResourceHandler("/images/**").addResourceLocations("/assets/images/");
        registry.addResourceHandler("/js/**").addResourceLocations("/assets/js/");
    }
    
}
``` 

#### References
+ [Bootstrap 설정](http://getbootstrap.com/getting-started/#download)
+ [Spring Framework Reference Documentation - 21.16.9. Serving of Resources](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#mvc-config-static-resources)

## 4. logging 설정
로깅을 위해 의존성을 추가합니다.
```xml
<project>
  ...
  <dependencies>
    ...
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
      <version>1.7.13</version>
    </dependency>
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>jcl-over-slf4j</artifactId>
      <version>1.7.13</version>
    </dependency>
    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
      <version>1.1.3</version>
    </dependency>
    <dependency>
      <groupId>org.logback-extensions</groupId>
      <artifactId>logback-ext-spring</artifactId>
      <version>0.1.4</version>
    </dependency>
  </dependencies>
  ...
</project>
```
추가되는 의존성 중에 logback-ext-spring 라이브러리는 로그백 설정 파일의 위치를 변경한다거나 java를 이용해 로그백 설정을 할 경우 필요합니다.
상기 내용이 필요 없는 경우 추가할 필요가 없습니다.

스프링 프레임워크 로깅 방식을 commons-logging 라이브러리에서 slf4j 라이브러리로 대체하기 위해 commons-logging에 대한 의존성을 가진 라이브러리가 있다면 commons-logging에 대한 의존성을 제외 시킵니다.
```xml
<project>
  ...
  <dependencies>
    ...
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-test</artifactId>
      <version>${org.springframework.version}</version>
      <scope>test</scope>
      <exclusions>
        <exclusion>
            <artifactId>commons-logging</artifactId>
            <groupId>commons-logging</groupId>
        </exclusion>
      </exclusions>
    </dependency>
    ...
  </dependencies>
  ...
</project>
``` 

xml을 이용해 로그백 설정을 할 경우에 Dispatcher Servlet Initializer에 다음 내용을 추가합니다.
사실상 해당 내용은 로그백 설정파일의 위치를 스프링 디스패처 서블릿에게 알려주기위해 설정하는 것 입니다.
로그백 설정파일의 위치를 클래스 패스 최상위에 위치시키거나 java를 이용해 로그백 설정을 할 경우 다음 내용은 필요없습니다.
```java
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    ...
    
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.addListener(new LogbackConfigListener());
        servletContext.setInitParameter("logbackConfigLocation", "classpath:kr/pe/jady/wordict/config/logging/logback.xml");
        super.onStartup(servletContext);
    }

}
```

logback 설정파일을 다음과 같이 작성하여 Dispatcher Servlet Initializer에서 설정한 경로에 위치 시킵니다.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xml>
<configuration debug="true"> 

  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender"> 
    <!-- encoders are  by default assigned the type
         ch.qos.logback.classic.encoder.PatternLayoutEncoder -->
    <encoder>
      <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %50.-50logger{36} - %msg%n</pattern>
    </encoder>
  </appender>
  
  <logger name="org.springframework" level="info" additivity="false">
    <appender-ref ref="STDOUT" />
  </logger>
  
  <logger name="kr.pe.jady.wordict" level="debug" additivity="false">
    <appender-ref ref="STDOUT" />
  </logger>

  <root level="info">
    <appender-ref ref="STDOUT" />
  </root>
</configuration>
```

java의 @Configuration을 이용해 로그백 설정을 할 경우 다음 설정을 추가합니다.
```java
package kr.pe.jady.wordict.config.logging;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.ext.spring.ApplicationContextHolder;

@Configuration
public class LogbackConfig {
    
    private static String DEFAULT_LOGGING_PATTERN = "%d{HH:mm:ss.SSS} [%thread] %-5level %50.-50logger{36} - %msg%n";
    
    @Bean
    public static ApplicationContextHolder applicationContextHolder() {
        return new ApplicationContextHolder();
    }
    
    @Bean
    public static LoggerContext loggerContext() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        return loggerContext;
    }
    
    @Bean (initMethod = "start", destroyMethod = "stop")
    public static PatternLayoutEncoder encoder (LoggerContext ctx) {
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(ctx);
        encoder.setPattern(DEFAULT_LOGGING_PATTERN);
        return encoder;
    }
    
    @Bean (initMethod = "start", destroyMethod = "stop")
    public static ConsoleAppender<ILoggingEvent> consoleAppender (LoggerContext ctx, PatternLayoutEncoder encoder) {
        ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<>();
        appender.setContext(ctx);
        appender.setEncoder(encoder);
        return appender;
    }
    
    @Bean
    public static Logger springframeworkLogger(LoggerContext loggerContext, ConsoleAppender<ILoggingEvent> consoleAppender) {
        return createLogger(loggerContext, "org.springframework", Level.INFO, consoleAppender);
    }
    
    @Bean
    public static Logger applicationLogger(LoggerContext loggerContext, ConsoleAppender<ILoggingEvent> consoleAppender) {
        return createLogger(loggerContext, "kr.pe.jady.wordict", Level.DEBUG, consoleAppender);
    }
    
    @Bean
    public static Logger rootLogger(LoggerContext loggerContext, ConsoleAppender<ILoggingEvent> consoleAppender) {
        return createLogger(loggerContext, Logger.ROOT_LOGGER_NAME, Level.INFO, consoleAppender);
    }
    
    @SafeVarargs
    private static Logger createLogger(LoggerContext loggerContext, String loggerName, Level logLevel, Appender<ILoggingEvent> ... appenders) {
        Logger logger = loggerContext.getLogger(loggerName);
        logger.detachAndStopAllAppenders();
        for (Appender<ILoggingEvent> appender : appenders) {
            logger.addAppender(appender);
        }
        logger.setLevel(logLevel);
        logger.setAdditive(false);
        return logger;
    }

}
```

#### References
+ [Bootstrap 설정](http://getbootstrap.com/getting-started/#download)
+ [Spring Framework Reference Documentation - 2.3.2. Logging](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#overview-logging)
+ [Logback 스프링 확장 라이브러리 설정](https://github.com/qos-ch/logback-extensions/wiki/Spring)
+ [Logback 레이아웃 설정](http://logback.qos.ch/manual/layouts.html)
+ [stackoverflow.com - Logback XML 스키마 또는 DTD 설정에 관한 문제](http://stackoverflow.com/questions/5731162/xml-schema-or-dtd-for-logback-xml)

## 5. Tiles 설정
타일즈 설정을 위해 의존성을 추가합니다.
```xml
<project>
  ...
  <dependencies>
    ...
    <dependency>
      <groupId>org.apache.tiles</groupId>
      <artifactId>tiles-extras</artifactId>
      <version>3.0.5</version>
    </dependency>
    ...
  </dependencies>
  ...
</project>
```

타일즈 설정 클래스를 추가합니다.
```java
package kr.pe.jady.wordict.config.tiles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.tiles3.SpringBeanPreparerFactory;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
public class TilesConfig {

    @Bean
    public TilesViewResolver getTilesViewResolver() {
        TilesViewResolver tilesViewResolver = new TilesViewResolver();
        tilesViewResolver.setOrder(0);
        tilesViewResolver.setViewClass(TilesView.class);
        return tilesViewResolver;
    }
    
    @Bean
    public TilesConfigurer getTilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions("classpath:**/tiles-definitions*.xml");
        tilesConfigurer.setPreparerFactoryClass(SpringBeanPreparerFactory.class);
        return tilesConfigurer;
    }
    
}
```

컨트롤러에서 반환된 뷰 이름을 통해 0순위로 타일즈 뷰를 찾고 없다면 jsp뷰를 찾도록 기존 뷰 리졸버 설정을 다음과 같이 변경합니다.
```java
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.order(1);
        registry.jsp().prefix("WEB-INF/views").suffix(".jsp");
    }
    
    ...
    
}
```

타일즈 설정 클래스에서 지정한 위치에 타일즈 페이지 정의 파일을 생성합니다.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE tiles-definitions PUBLIC
       "-//Apache Software Foundation//DTD Tiles Configuration 3.0//EN"
       "http://tiles.apache.org/dtds/tiles-config_3_0.dtd">
<tiles-definitions>
  <definition name="abstarctDefaultDefinition" template="/WEB-INF/views/layout/default/template.jsp">
    <put-attribute name="title" value="프로젝트 단어사전" />
    <put-attribute name="header" value="/WEB-INF/views/layout/default/header.jsp" />
    <put-attribute name="menu" value="/WEB-INF/views/layout/default/menu.jsp" />
    <put-attribute name="body" value="" />
  </definition>
  <definition name="dict/*" extends="abstarctDefaultDefinition">
    <put-attribute name="body" value="/WEB-INF/views/dict/{1}.jsp" />
  </definition>
  <definition name="dict/*/*" extends="abstarctDefaultDefinition">
    <put-attribute name="body" value="/WEB-INF/views/dict/{1}/{2}.jsp" />
  </definition>
  <definition name="dict/*/*/*" extends="abstarctDefaultDefinition">
    <put-attribute name="body" value="/WEB-INF/views/dict/{1}/{2}/{3}.jsp" />
  </definition>
  <definition name="dict/*/*/*/*" extends="abstarctDefaultDefinition">
    <put-attribute name="body" value="/WEB-INF/views/dict/{1}/{2}/{3}/{4}.jsp" />
  </definition>
</tiles-definitions>
```

#### References
+ [Tiles tutorial](http://tiles.apache.org/framework/tutorial/index.html)
+ [Spring Framework Reference Documentation - 22.8. Tiles](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#view-tiles)
+ [Spring 어노테이션 기반의 Tiles Definitions 설정](http://springbybhimu.blogspot.kr/2015/01/spring-4-and-tiles-3-programmatic.html)

## 6. Requirejs 설정

## 7. JPA 설정
