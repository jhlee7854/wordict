package kr.pe.jady.wordict.config.spring.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/external/**").addResourceLocations("/assets/external/");
		registry.addResourceHandler("/css/**").addResourceLocations("/assets/css/");
		registry.addResourceHandler("/images/**").addResourceLocations("/assets/images/");
		registry.addResourceHandler("/js/**").addResourceLocations("/assets/js/");
	}
	
}
