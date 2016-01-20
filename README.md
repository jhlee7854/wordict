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
</project>
```

pom.xml의 설정에서 사용될 프로퍼티를 설정합니다.
```xml
<project>
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.build.compile>1.8</project.build.compile>
  </properties>
</project>
```

JSP를 UI 템플릿으로 사용하기 위하여 의존성을 추가합니다.
```xml
<project>
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
  </dependencies>
</project>
```

빌드를 위해 Maven Compiler Plugin을 설정합니다.
```xml
<project>
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
    </plugins>
  </build>
</project>
```

웹 어플리케이션이므로 war패키징을 위해 Maven War Plugin을 설정합니다.
```xml
<project>
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-war-plugin</artifactId>
        <version>2.6</version>
        <configuration>
          <failOnMissingWebXml>false</failOnMissingWebXml>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>
```

웹 어플리케이션을 구동하여 확인해 볼 수 있도록 Tomcat Maven Plugin을 설정합니다.
```xml
<project>
  <build>
    <plugins>
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
    </plugins>
  </build>
</project>
```

다음 명령을 통해 웹 어플리케이션을 구동할수 있습니다.
```sh
mvn tomcat7:run
```

## 2. Spring MVC 설정
Spring MVC 설정을 위해 의존성을 추가합니다.
```xml
<project>
  <dependencies>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>${org.springframework.version}</version>
    </dependency>
  </dependencies>
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
  <dependencies>
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
  </dependencies>
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

## 3. Tiles 설정

## 4. Bootstrap 설정

## 5. Requirejs 설정

## 6. JPA 설정

## References
+ [Tomcat Maven Plugin 설정](https://tomcat.apache.org/maven-plugin-trunk/tomcat7-maven-plugin/)
+ [Spring Framework Reference Documentation - 14.6. Spring MVC Test Framework](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#spring-mvc-test-framework)
+ [Spring Framework Reference Documentation - 21.15. Code-based Servlet container initialization](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#mvc-container-config)
+ [Spring Framework Reference Documentation - 21.16. Configuring Spring MVC](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#mvc-config)