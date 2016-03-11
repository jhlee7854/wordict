package kr.pe.jady.wordict.config.spring.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"kr.pe.jady.**.service"},
        includeFilters = @ComponentScan.Filter(value = {Service.class}),
        excludeFilters = @ComponentScan.Filter(value = {Controller.class, Repository.class}))
public class AppConfig {
}
