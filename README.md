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

## 3. Tiles 설정

## 4. Bootstrap 설정

## 5. Requirejs 설정

## 6. JPA 설정

## References
+ [Tomcat Maven Plugin 설정](https://tomcat.apache.org/maven-plugin-trunk/tomcat7-maven-plugin/)